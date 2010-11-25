package org.cggh.chassis.manta.xquery.functions.util.config;

import java.lang.reflect.Method;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.exist.dom.QName;
import org.exist.xquery.BasicFunction;
import org.exist.xquery.Cardinality;
import org.exist.xquery.FunctionSignature;
import org.exist.xquery.XPathException;
import org.exist.xquery.XQueryContext;
import org.exist.xquery.value.FunctionParameterSequenceType;
import org.exist.xquery.value.FunctionReturnSequenceType;
import org.exist.xquery.value.Sequence;
import org.exist.xquery.value.SequenceType;
import org.exist.xquery.value.StringValue;
import org.exist.xquery.value.Type;

public class GetJNDIVariable extends BasicFunction {
	protected static final Logger logger = Logger
			.getLogger(GetJNDIVariable.class);

	public final static FunctionSignature signature = new FunctionSignature(
			new QName("get-jndi-variable", MantaUtilModule.NAMESPACE_URI,
					MantaUtilModule.PREFIX),
			"Returns the value of a variable declared using JNDI.",
			new SequenceType[] { new FunctionParameterSequenceType("bean",
					Type.STRING, Cardinality.EXACTLY_ONE,
					"The name of the bean to retrieve"),
					new FunctionParameterSequenceType("name",
							Type.STRING, Cardinality.EXACTLY_ONE,
							"The name of the variable to retrieve the value of.")},
			new FunctionReturnSequenceType(Type.STRING,
					Cardinality.EXACTLY_ONE, "the value of the variable"));

	public GetJNDIVariable(XQueryContext context) {
		super(context, signature);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.exist.xquery.BasicFunction#eval(org.exist.xquery.value.Sequence[],
	 * org.exist.xquery.value.Sequence)
	 */
	public Sequence eval(Sequence[] args, Sequence contextSequence)
			throws XPathException {

		String bean = args[0].getStringValue();
		String name = args[1].getStringValue();
		String ret = "";
		String methodName = "";
		Object o;
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");

			o = envContext.lookup(bean);
			
			if (o == null) {
				logger.error("Unable to find JNDI context for " + bean + ". Check your app server configuration.");
			}
			//Using reflection here so that new configuration classes can be added just by creating the appropriate bean
			methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
			@SuppressWarnings("rawtypes")
			Class[] parameterTypes = {};
			Method m = o.getClass().getMethod(methodName, parameterTypes);
			ret = (String) m.invoke(o, new Object[0]);
			
		} catch (Exception e) {
			logger.error("Unable to find method "+ methodName + " for context " + bean + ". Check your app server configuration.");
			throw new XPathException(e);
		}

		if (logger.isDebugEnabled()) {
			String value = ret;
			if (name.toLowerCase().indexOf("password") >= 0) {
				value = "A PASSWORD";
			}
			String msg = "Configuration variable:" + name + " is " + value;
			logger.debug(msg);
		}
		
		return new StringValue(ret);

	}

}
