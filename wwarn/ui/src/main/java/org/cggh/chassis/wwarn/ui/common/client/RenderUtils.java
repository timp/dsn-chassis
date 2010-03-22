package org.cggh.chassis.wwarn.ui.common.client;

import java.util.Collection;
import java.util.List;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

public class RenderUtils {

	
	
	
	/**
	 * Render a flex table from a list of widget arrays.
	 * 
	 * @param in the list of widget arrays to put into a flex table
	 * 
	 * @return a flex table
	 */
	public static FlexTable renderResultsTable(List<Widget[]> in) {
		FlexTable out = new FlexTable();
		out.setCellPadding(0);
		out.setCellSpacing(0);
		out.setBorderWidth(0);
		
		for (int r=0; r<in.size(); r++) {
			Widget[] row = in.get(r);
			for (int c=0; c<row.length; c++) {
				Widget w = row[c];
				w.addStyleName(CommonStyles.RESULTSTABLE_WIDGET);
				out.setWidget(r, c, w);
			}
			if (r % 2 == 0) 
				out.getRowFormatter().addStyleName(r, CommonStyles.RESULTSTABLE_EVEN);
			else 
				out.getRowFormatter().addStyleName(r, CommonStyles.RESULTSTABLE_ODD);
			out.getCellFormatter().addStyleName(r, 0, CommonStyles.RESULTSTABLE_FIRSTCELL);
		}

		out.addStyleName(CommonStyles.RESULTSTABLE);
		out.getRowFormatter().addStyleName(0, CommonStyles.RESULTSTABLE_FIRSTROW);
		
		return out;
	}

	public static String[] extractEmails(String messilyDelimitedEmailString) {
		messilyDelimitedEmailString = messilyDelimitedEmailString.replaceAll("\n", " ");
		messilyDelimitedEmailString = messilyDelimitedEmailString.replaceAll(",", " ");
		messilyDelimitedEmailString = messilyDelimitedEmailString.replaceAll(" +", " ");
		messilyDelimitedEmailString = messilyDelimitedEmailString.trim();
		messilyDelimitedEmailString = messilyDelimitedEmailString.replaceAll(" ", "|");
		return messilyDelimitedEmailString.split("\\|");
	}
	
	
	public static String join (Collection<?> collection, String separator) {
		
		if (collection != null) {
			
			if (separator == null)
				separator = "";
		    StringBuilder sb = new StringBuilder();
		    int count = 0;
		    for(Object entry : collection) {
		    	if (count > 0)
		    		sb.append(separator);
		    	count++;
		    	if (entry != null) {
		    		sb.append(entry.toString());
		    	}
		    }
		    return sb.toString();
	    
		} else {
		
			return "";
		}
	}
	
	
	
	

}
