package org.cggh.chassis.manta.xquery.functions.util.config;

import java.util.Arrays;

import org.exist.xquery.AbstractInternalModule;
import org.exist.xquery.FunctionDef;
import org.exist.xquery.XPathException;

@SuppressWarnings("unchecked")
public class MantaUtilModule extends AbstractInternalModule {

	public static final String NAMESPACE_URI = "http://www.cggh.org/2010/chassis/manta-util/xmlns";
	public static final String PREFIX = "chassis-util";

	public static final FunctionDef[] functions = { new FunctionDef(
			GetJNDIVariable.signature, GetJNDIVariable.class) };

	static {
		Arrays.sort(functions, new FunctionComparator());
	}

	public MantaUtilModule() throws XPathException {
		super(functions, true);
	}

	@Override
	public String getDefaultPrefix() {
		return PREFIX;
	}

	@Override
	public String getDescription() {
		return "A module providing Java utilities for AtomBeat.";
	}

	@Override
	public String getNamespaceURI() {
		return NAMESPACE_URI;
	}

	@Override
	public String getReleaseVersion() {
		return "TODO";
	}

}
