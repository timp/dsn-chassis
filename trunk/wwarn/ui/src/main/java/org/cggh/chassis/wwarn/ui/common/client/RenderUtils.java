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
	
	public static FlexTable renderResultItemTable(List<Widget[]> rows) {
		
		FlexTable out = new FlexTable();
		out.setCellPadding(0);
		out.setCellSpacing(0);
		out.setBorderWidth(0);
		
		for (int r = 0; r < rows.size(); r++) {
			
			Widget[] row = rows.get(r);
			for (int c=0; c<row.length; c++) {
				Widget w = row[c];
				w.addStyleName(CommonStyles.RESULTITEMTABLE_WIDGET);
				out.setWidget(r, c, w);
			}

			out.getCellFormatter().addStyleName(r, 0, CommonStyles.RESULTITEMTABLE_FIRSTCOLUMN);
		}

		out.addStyleName(CommonStyles.RESULTITEMTABLE);
		
		return out;
	}
	
	public static FlexTable renderFirstRowHeadingResultsAsFirstColumnHeadingTable(List<Widget[]> rows) {
		
		int  fieldCount = -1;
		for (int i = 0; i < rows.size(); i++) {
			
			Widget[] row = rows.get(i);
			if (fieldCount != -1)
				if (row.length != fieldCount)
					throw new RuntimeException("Current row length ("+row.length+") different to previous ("+fieldCount+")");
			fieldCount = row.length;
		}

		FlexTable table = new FlexTable();
		table.addStyleName(CommonStyles.RESULTITEMTABLE);
		table.setCellPadding(0);
		table.setCellSpacing(0);
		table.setBorderWidth(0);

		for (int field = 0; field < fieldCount; field++) {
			
			for (int i = 0; i < rows.size(); i++) {
				Widget[] row = rows.get(i);
				Widget cellValue = row[field];
				if(i != 0)
					cellValue.addStyleName(CommonStyles.RESULTITEMTABLE_WIDGET);
				table.setWidget(field, i, cellValue);
			}
			table.getCellFormatter().addStyleName(field, 0, CommonStyles.RESULTITEMTABLE_FIRSTCOLUMN);
		}

		
		return table;
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
	
	/** 2010-03-22T20:55:07+00:00 becomes 2010-03-22 */
	public static String timstampAsDate(String timestamp) { 
		if (timestamp.length() != 25)
			throw new RuntimeException("Expecting a timestamp of length 25 eg 2010-03-22T20:55:07+00:00");
		return timestamp.substring(0,10); 
	}
	
	/** 2010-03-22T20:55:07+00:00 becomes 2010-03-22 20:55 */
	public static String timstampToTheMinute(String timestamp) { 
		if (timestamp.length() != 25)
			throw new RuntimeException("Expecting a timestamp of length 25 eg 2010-03-22T20:55:07+00:00");
		return timestamp.substring(0,16).replace("T", " "); 
	}

}
