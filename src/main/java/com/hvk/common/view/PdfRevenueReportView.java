package com.hvk.common.view;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.hvk.societmaintain.model.BillDetail;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class PdfRevenueReportView extends AbstractPdfView{

	@Override
	protected void buildPdfDocument(Map model, Document document,
			PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String billId =
				ServletRequestUtils.getStringParameter(request, "billId");
		
		List<BillDetail> billDetails = (List<BillDetail>) model.get("billDetailList");
		BillDetail billDetail = billDetails.get(Integer.valueOf(billId)-1);
		
		 PdfPTable table = new PdfPTable(2);
	        // the cell object
	        PdfPCell cell;
	        // we add a cell with colspan 3
	        cell = new PdfPCell(new Phrase("Maintainance Bill"));
	        cell.setColspan(2);
	        table.addCell(cell);
	        table.addCell("Bill No");
			table.addCell(billDetail.getBillId());
	        // now we add a cell with rowspan 2
	        cell = new PdfPCell(new Phrase("Address"));
	        cell.setRowspan(2);
	        table.addCell(cell);
	        // we add the four remaining cells with addCell()
	        table.addCell(billDetail.getApartmentId() +"/"+ billDetail.getBuildingName());
	        table.addCell(billDetail.getApartmentTypeDescription() +","+billDetail.getComplexDescription() );
	        table.addCell("Name:");
			table.addCell(billDetail.getFirstName() +" "+ billDetail.getLastName());
			
			table.addCell("Bill Date");
			table.addCell(String.format("%1$td-%1$tm-%1$tY", billDetail.getIssueDate()));
			table.addCell("Last Date for Bill Payment");
			table.addCell(String.format("%1$td-%1$tm-%1$tY", billDetail.getPaymentDueDate()));
			table.addCell("Bill Amount");
			table.addCell(billDetail.getOriginalAmountDue()+"");
			table.getDefaultCell().setBackgroundColor(Color.RED);
		/*Table table = new Table(2);
	
		
		Font font = new Font(Font.COURIER, 10, Font.BOLD); // 1
		font.setColor(new Color(0x92, 0x90, 0x83));
		
		Chunk chunk = 
				new Chunk("testing text element", font); // 2
			
			chunk.setBackground(new Color(0xff, 0xe4, 0x00)); // 3
			

			document.add(chunk); // 4
		table.setAlignment(	Table.ALIGN_TOP);
		
		table.addCell("Bill No");
		table.addCell(billDetail.getBillId());
		table.addCell("Address");
		table.addCell(billDetail.getApartmentId() +"/"+ billDetail.getBuildingName());
		table.addCell("");
		table.addCell(billDetail.getApartmentTypeDescription());
		table.addCell("Apartment Complex");
		table.addCell(billDetail.getApartmentTypeDescription());
		table.addCell("Name:");
		table.addCell(billDetail.getFirstName() +" "+ billDetail.getLastName());
		
		table.addCell("Bill Date");
		table.addCell(billDetail.getIssueDate()+"");
		table.addCell("Last Date for Bill Payment");
		table.addCell(billDetail.getPaymentDueDate()+"");
		table.addCell("Bill Amount");
		table.addCell(billDetail.getOriginalAmountDue()+"");
		table.setWidth(100f);
        //table.getDefaultCell().setPadding(3);
        table.getDefaultCell().setUseAscender(true);
        table.getDefaultCell().setUseDescender(true);
        table.getDefaultCell().setColspan(5);
        table.getDefaultCell().setBackgroundColor(Color.RED);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table.getDefaultCell().setColspan(1);
        table.getDefaultCell().setBackgroundColor(Color.ORANGE);
        table.getDefaultCell().setVerticalAlignment(Table.ALIGN_CENTER);
       */
		/*
		 PdfPTable table = new PdfPTable(new float[] { 2, 1, 2, 5, 1 });
	        table.setWidthPercentage(100f);
	        table.getDefaultCell().setPadding(3);
	        table.getDefaultCell().setUseAscender(true);
	        table.getDefaultCell().setUseDescender(true);
	        table.getDefaultCell().setColspan(5);
	        table.getDefaultCell().setBackgroundColor(Color.RED);
	        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
	       // table.addCell(day.toString());
	        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
	        table.getDefaultCell().setColspan(1);
	        table.getDefaultCell().setBackgroundColor(Color.ORANGE);
	        for (int i = 0; i < 2; i++) {
	            table.addCell("Bill No");
	            table.addCell("Address");
	            table.addCell("Apartment Complex");
	            table.addCell("Name");
	            table.addCell("Bill Date");
	        }
	        table.getDefaultCell().setBackgroundColor(null);
	        table.setHeaderRows(3);
	        table.setFooterRows(1);
	        
	            
	            table.addCell(billDetail.getBillId());
	            table.addCell(billDetail.getApartmentId() +"/"+ billDetail.getBuildingName());
	            table.addCell(billDetail.getComplexDescription());
	            table.addCell(billDetail.getFirstName() +" "+ billDetail.getLastName());
	            table.addCell(billDetail.getIssueDate()+"");
	     
		
		*/
		
		
		
		document.add(table);
		
	}
	
	/**
     * Draws a background for every other row.
     * @see com.itextpdf.text.pdf.PdfPTableEvent#tableLayout(
     *      com.itextpdf.text.pdf.PdfPTable, float[][], float[], int, int,
     *      com.itextpdf.text.pdf.PdfContentByte[])
     */
    public void tableLayout(PdfPTable table, float[][] widths, float[] heights,
        int headerRows, int rowStart, PdfContentByte[] canvases) {
        int columns;
        Rectangle rect;
        int footer = widths.length - table.getFooterRows();
        int header = table.getHeaderRows() - table.getFooterRows() + 1;
        for (int row = header; row < footer; row += 2) {
            columns = widths[row].length - 1;
            rect = new Rectangle(widths[row][0], heights[row],
                        widths[row][columns], heights[row + 1]);
            rect.setBackgroundColor(Color.YELLOW);
            rect.setBorder(Rectangle.NO_BORDER);
            canvases[PdfPTable.BASECANVAS].rectangle(rect);
        }
    }
	
}