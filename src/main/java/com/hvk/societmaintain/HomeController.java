package com.hvk.societmaintain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.hvk.societmaintain.model.BillDetail;
import com.hvk.societmaintain.model.RefDataResponse;
import com.hvk.societmaintain.model.RegisterBiller;
import com.hvk.societmaintain.model.Society;
import com.hvk.societmaintain.model.SocietyResponse;
import com.hvk.societmaintain.model.UserSummary;

/**
 * Handles requests for the application home page.
 */
@SessionAttributes({ "billDetailList", "userSummary" })
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	@Inject
	private ReferenceDataRepository referenceRepository;

	/**
	 * Prepares the Model with some metadata and the list of States retrieved
	 * from the DB. Then, selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String home(ModelMap model) {
		logger.info("Welcome home!");
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		List<UserSummary> usrsumm = referenceRepository
				.getUserSummaryDetails(user);
		UserSummary userSummary = usrsumm.get(0);
		System.out.println("userSummary.getFirstName()"
				+ userSummary.getFirstName());

		Object obj = referenceRepository.executeProc("testProc1",
				userSummary.getUserId());
		if (null != obj) {
			String[][] retrievedData = (String[][]) obj;
			List<BillDetail> billDetails = new ArrayList<BillDetail>();
			System.out.println(retrievedData.length);

			for (int i = 0; i < retrievedData.length; i++) {
				String[] row = retrievedData[i];

				BillDetail billdetail = new BillDetail();
				billdetail.setBillId(row[0]);
				billdetail.setApartmentId(row[1]);
				billdetail.setBuildingName(row[2]);
				billdetail.setComplexDescription(row[3]);
				billdetail.setFirstName(row[4]);
				billdetail.setLastName(row[5]);
				billdetail.setApartmentTypeDescription(row[6]);
				billdetail.setUtilityTypeDescription(row[7]);
				billdetail.setOriginalAmountDue(Double.valueOf(row[8]));
				billdetail.setAmountOutstanding(Double.valueOf(row[9]));
				

				try {
					billdetail.setPaymentDueDate(new SimpleDateFormat(
							"yyyy-MM-dd").parse(row[10]));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					billdetail.setIssueDate(new SimpleDateFormat("yyyy-MM-dd")
							.parse(row[11]));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				billdetail.setFormattedIssueDate(String.format("%1$td-%1$tm-%1$tY", billdetail.getIssueDate()));
				billdetail.setFormattedPaymentDueDate(String.format("%1$td-%1$tm-%1$tY", billdetail.getPaymentDueDate()));

				billDetails.add(billdetail);

			}
			

			model.addAttribute("billDetailList", billDetails);
		}
		model.addAttribute("userSummary", usrsumm);
		return "home";
	}

	@RequestMapping(value = "/welcome/society/{name}")
	public @ResponseBody
	SocietyResponse getSocietyDetails(ModelMap model, @PathVariable String name) {
		SocietyResponse response = new SocietyResponse();
		Society society = new Society();
		society.setName(name);
		society.setFullName(name + "desc");
		society.setAddress(name + "address");
		response.setSociety(society);
		return response;

	}

	@RequestMapping(value = "/welcome/referenceData/society")
	public @ResponseBody
	List<RefDataResponse> getSocietyReferenceData(ModelMap model) {
		return referenceRepository.findSocieties();
	}

	@RequestMapping(value = "/welcome/referenceData/apartmentBuildings")
	public @ResponseBody
	List<RefDataResponse> getApartmentBuildingsReferenceData(ModelMap model) {
		return referenceRepository.findApartmentBuildings();
	}

	@RequestMapping(value = "/welcome/referenceData/apartments")
	public @ResponseBody
	List<RefDataResponse> getApartmentsReferenceData(ModelMap model) {
		return referenceRepository.findApartments();
	}

	@RequestMapping(value = "/welcome/referenceData/ownershipType")
	public @ResponseBody
	List<RefDataResponse> getOwnershipTypeReferenceData(ModelMap model) {
		return referenceRepository.findOwnershipType();
	}

	@RequestMapping(value = "/welcome/referenceData/utilityType")
	public @ResponseBody
	List<RefDataResponse> getUtilityTypeReferenceData(ModelMap model) {
		return referenceRepository.findUtilityType();
	}

	@RequestMapping(value = "/welcome/referenceData/registerBiller", method = { RequestMethod.POST })
	public @ResponseBody
	int registerBill(@RequestBody RegisterBiller biller, ModelMap model) {
		System.out.println("biller called");

		System.out.println("biller complex" + biller.getComplex());
		List<UserSummary> userSummList = (List<UserSummary>) model
				.get("userSummary");
		// referenceRepository.registerBiller();
		return referenceRepository.registerBiller(biller, userSummList.get(0));
	}
	
	
	@RequestMapping(value = "/welcome/updateBill", method = { RequestMethod.POST })
	public @ResponseBody
	int updateBill(@RequestBody BillDetail biller, ModelMap model) {
		System.out.println("updateBill called");

		System.out.println("updateBill complex" + biller.getPaidAmount());
		
		return 0;
	}
	
	
	@RequestMapping(value = "/welcome/retreiveBill/{billId}")
	public @ResponseBody
	BillDetail retreiveBill(ModelMap model,  @PathVariable final String billId) {
		
		List<BillDetail> billDetails = (List<BillDetail>) model.get("billDetailList");
		System.out.println("retreiveBill"+billId);
		BillDetail billDetail = billDetails.get(Integer.valueOf(billId)-1);
		return billDetail;
	}
	

	@RequestMapping(value = "/welcome/registerBillerPage")
	public @ResponseBody
	UserSummary registerBillPage(ModelMap model) {
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		List<UserSummary> usrsumm = referenceRepository
				.getUserSummaryDetails(user);
		UserSummary userSummary = null;
		for (Iterator<UserSummary> iterator = usrsumm.iterator(); iterator
				.hasNext();) {
			userSummary = iterator.next();
			System.out.println("userSummary.getFirstName()"
					+ userSummary.getFirstName());
		}
		return userSummary;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(ModelMap model) {

		return "login";

	}

	@RequestMapping(value = "/revenuereport/{output}", method = RequestMethod.GET)
	public String getBill(ModelMap model, @PathVariable final String output) {

		if (output == null || "".equals(output)) {
			// return normal view
			return "RevenueSummary";

		} else if ("PDF".equals(output.toUpperCase())) {
			// return excel view
			return "PdfRevenueSummary";

		} else {
			// return normal view
			return "RevenueSummary";

		}
	}

	@RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
	public String loginerror(ModelMap model) {

		model.addAttribute("error", "true");
		return "login";

	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {

		return "login";

	}
}
