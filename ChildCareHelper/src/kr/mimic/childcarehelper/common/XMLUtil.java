package kr.mimic.childcarehelper.common;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.ContentValues;
import android.util.Log;

public class XMLUtil {
	public static XMLUtil _instance = null;

	// Data.org
	private final String SOAP_HEADER  = "soap:Header";
	private final String SOAP_BODY    = "soap:Body";
	private final String LIST_DATA    = "LocalChildFacilityList";
	// Daum DNA API
	private final String DAUM_CHANNEL = "channel";
	private final String DAUM_ITEM    = "item";

	public static XMLUtil getInstance(){
		return (_instance == null)?_instance = new XMLUtil():_instance;
	}

	/**
	 * 서버에 요청할 XML Data를 생성한다.
	 * 
	 * @param servicekey
	 * @param content
	 * @return
	 */
	public String generatedXML(String servicekey, ContentValues content){
		StringBuffer sb           = new StringBuffer();
		Set<Entry<String, Object>> keys          = content.valueSet();
		Iterator<Entry<String, Object>> iterator = keys.iterator();
	/* ContentValues.keySet() Since : API Level 11
	 * Current min API Level : API Level 9
		Set<String> keys          = content. keySet();
		Iterator<String> iterator = keys.iterator();
	 */

		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
		  .append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:q0=\"http://apiiseoul.seoul.go.kr/SearchLocalChildcareInformationService/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">")
		  .append("<soapenv:Header>")
		  .append("<q0:ComMsgHeaderRequest>")
		  .append("<RequestMsgID/>")
		  .append("<ServiceKey>").append(servicekey).append("</ServiceKey>")
		  .append("<RequestTime/>")
		  .append("<CallBackURI/>")
		  .append("<priMsgHeader>")
		  .append("<test_val1/>")
		  .append("<test_val2/>")
		  .append("</priMsgHeader>")
		  .append("</q0:ComMsgHeaderRequest>")
		  .append("</soapenv:Header>")
		  .append("<soapenv:Body>")
		  .append("<q0:LocalChildFacilityListRequest>");

		while(iterator.hasNext()){
			String key = iterator.next().getKey();
			/* ContentValues.keySet() Since : API Level 11
			 * Current min API Level : API Level 9
				String key = iterator.next().toString();
			 */

			sb.append("<").append(key).append(">")
			  .append(content.getAsString(key))
			  .append("</").append(key).append(">");
		}

		sb.append("</q0:LocalChildFacilityListRequest>")
		  .append("</soapenv:Body>")
		  .append("</soapenv:Envelope>");

		LogUtil.log(Log.ERROR, this, "generatedXML()", "Request XML : " + sb.toString());

		return sb.toString();
	}

	/**
	 * 서버에 요청할 Parameter를 생성한다.
	 * 
	 * @param servicekey
	 * @param content
	 * @return
	 */
	public String generatedParams(String servicekey, ContentValues content){
		StringBuffer sb = new StringBuffer();
		Set<Entry<String, Object>> keys          = content.valueSet();
		Iterator<Entry<String, Object>> iterator = keys.iterator();

		try {
			sb.append("apikey=").append(URLEncoder.encode(Constants.getServiceKey(Constants.DAUM_LOCAL_API), "UTF-8"));
			
			while(iterator.hasNext()){
				String key = iterator.next().getKey();
				sb.append("&").append(key).append("=").append(URLEncoder.encode(content.getAsString(key), "UTF-8"));
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		LogUtil.log(Log.ERROR, this, "generatedParams()", "Request Params : " + sb.toString());

		return sb.toString();
	}

	public ArrayList<ContentValues> parsedXML(String xml){
		ArrayList<ContentValues> retDataList = new ArrayList<ContentValues>();
		ContentValues contents = null;

		retDataList.clear();
		LogUtil.log(Log.ERROR, this, "parsedXML(xml)", "XML : " + xml);

		try {
			XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();

			parser.setInput(new ByteArrayInputStream(xml.getBytes("UTF-8")), "UTF-8");

			String tag = new String();
			int parserEvent = parser.getEventType();
			int parserDepth;

			boolean isBody;
			boolean isData;	// Child Node Of LocalChildFacilityList
			// Header - SOAP
			boolean isRequestMsgID  = false;
			boolean isResponseTime  = false;
			boolean isResponseMsgID = false;
			boolean isSuccessYN     = false;
			boolean isReturnCode    = false;
			boolean isErrMsg        = false;

			// Body - SOAP
			boolean isTotalFacilifyCount = false;
			boolean isTotalPageNumber    = false;
			boolean isPageNumber         = false;
			boolean isPageFacilityCount  = false;
			// Body - Daum
			boolean isChannelTitle  = false;
			boolean isDescription   = false;
			boolean isGenerator     = false;
			boolean isLink          = false;
			boolean isLastBuildDate = false;
			boolean isTotalCount    = false;
			boolean isResult        = false;
			

			// LocalChildFacilityList
			boolean isListNumber    = false;
			boolean isFacilityName  = false;	// LocalChildFacilityItemResponse 중복
			boolean isType          = false;	
			boolean isCertification = false;	// LocalChildFacilityItemResponse 중복
			boolean isSeoul         = false;
			boolean isFixedNumber   = false;	// LocalChildFacilityItemResponse 중복
			boolean isPresentNumber = false;	// LocalChildFacilityItemResponse 중복
			boolean isTelephone     = false;	// LocalChildFacilityItemResponse 중복
			boolean isFax           = false;
			boolean isAddress       = false;	// LocalChildFacilityItemResponse 중복
			boolean isFacilityID    = false;
			// LocalChildFacilityItemResponse
			boolean isCRType          = false;
			boolean isCRSpec          = false;
			boolean isPresidentName   = false;
			boolean isOpenDate        = false;
			boolean isVehicle         = false;
			boolean isGovSupport      = false;
			boolean isAccidentIns     = false;
			boolean isFireIns         = false;
			boolean isCompensationIns = false;
			// item - Daum
			boolean isId                 = false;
			boolean isItemTitle          = false;
			boolean isMountain           = false;
			boolean isLocalName_1        = false;
			boolean isLocalName_2        = false;
			boolean isLocalName_3        = false;
			boolean isMainAddress        = false;
			boolean isSubAddress         = false;
			boolean isBuildingAddress    = false;
			boolean isIsNewAddress       = false;
			boolean isNewAddress         = false;
			boolean isLng                = false;
			boolean isLat                = false;
			boolean isPlaceName          = false;
			boolean isPointWx            = false;
			boolean isPointWy            = false;

			do{
				tag         = parser.getName();
				parserDepth = parser.getDepth();

				switch (parserEvent) {
				case XmlPullParser.START_TAG:
					if(SOAP_HEADER.equals(tag)){
						contents = new ContentValues();
					}else if(SOAP_BODY.equals(tag)){
						contents = new ContentValues();
					}else if(LIST_DATA.equals(tag)){
						retDataList.add(contents);
						contents = new ContentValues();
					}else if(DAUM_CHANNEL.equals(tag)){
						contents = new ContentValues();
					}else if(DAUM_ITEM.equals(tag)){
						retDataList.add(contents);
						contents = new ContentValues();
					}

					// Header
					if("RequestMsgID".equals(tag))	isRequestMsgID  = true;
					if("ResponseTime".equals(tag))	isResponseTime  = true;
					if("ResponseMsgID".equals(tag))	isResponseMsgID = true;
					if("SuccessYN".equals(tag))		isSuccessYN     = true;
					if("ReturnCode".equals(tag))	isReturnCode    = true; 
					if("ErrMsg".equals(tag))		isErrMsg        = true;

					// Body
					if("TotalFacilifyCount".equals(tag))	isTotalFacilifyCount = true;
					if("TotalPageNumber".equals(tag))		isTotalPageNumber    = true;
					if("PageNumber".equals(tag))			isPageNumber         = true;
					if("PageFacilityCount".equals(tag))		isPageFacilityCount  = true;

					// LocalChildFacilityList
					if("ListNumber".equals(tag))	isListNumber    = true;
					if("FacilityName".equals(tag))	isFacilityName  = true;
					if("Type".equals(tag))			isType          = true;
					if("Certification".equals(tag))	isCertification = true;
					if("Seoul".equals(tag))			isSeoul         = true;
					if("FixedNumber".equals(tag))	isFixedNumber   = true;
					if("PresentNumber".equals(tag))	isPresentNumber = true;
					if("Telephone".equals(tag))		isTelephone     = true;
					if("Fax".equals(tag))			isFax           = true;
					if("Address".equals(tag))		isAddress       = true;
					if("FacilityID".equals(tag))	isFacilityID    = true;

					// LocalChildFacilityItemResponse
					if("CRType".equals(tag))				isCRType          = true;
					if("CRSpec".equals(tag))				isCRSpec          = true;
					if("PresidentName".equals(tag))			isPresidentName   = true;
					if("OpenDate".equals(tag))				isOpenDate        = true;
					if("Vehicle".equals(tag))				isVehicle         = true;
					if("GovSupport".equals(tag))			isGovSupport      = true;
					if("AccidentInsurance".equals(tag))		isAccidentIns     = true;
					if("FireInsurance".equals(tag))			isFireIns         = true;
					if("CompensationInsurance".equals(tag))	isCompensationIns = true;

					// Body - Daum
					if(
						"title".equals(tag) && 
						(parserDepth == 2)
					)								isChannelTitle  = true;
					if("description".equals(tag))	isDescription   = true;
					if("generator".equals(tag))		isGenerator     = true;
					if("link".equals(tag))			isLink          = true;
					if("lastBuildDate".equals(tag))	isLastBuildDate = true;
					if("totalCount".equals(tag))	isTotalCount    = true;
					if("result".equals(tag))		isResult        = true;

					// item - Daum
					if("id".equals(tag))				isId              = true;
					if(
						"title".equals(tag) && 
						(parserDepth == 3)
					)									isItemTitle       = true;
					if("mountain".equals(tag))			isMountain        = true;
					if("localName_1".equals(tag))		isLocalName_1     = true;
					if("localName_2".equals(tag))		isLocalName_2     = true;
					if("localName_3".equals(tag))		isLocalName_3     = true;
					if("mainAddress".equals(tag))		isMainAddress     = true;
					if("subAddress".equals(tag))		isSubAddress      = true;
					if("buildingAddress".equals(tag))	isBuildingAddress = true;
					if("isNewAddress".equals(tag))		isIsNewAddress    = true;
					if("newAddress".equals(tag))		isNewAddress      = true;
					if("lng".equals(tag))				isLng             = true;
					if("lat".equals(tag))				isLat             = true;
					if("placeName".equals(tag))			isPlaceName       = true;
					if("point_wx".equals(tag))			isPointWx         = true;
					if("point_wy".equals(tag))			isPointWy         = true;

					LogUtil.log(Log.ERROR, this, "parsedXML()", "Depth : (" + parser.getDepth() + ") <" + parser.getName() + ">");
					break;
				case XmlPullParser.TEXT:
					// Header
					if(isRequestMsgID)	contents.put("RequestMsgID", parser.getText());
					if(isResponseTime)	contents.put("ResponseTime", parser.getText());
					if(isResponseMsgID)	contents.put("ResponseMsgID", parser.getText());
					if(isSuccessYN)		contents.put("SuccessYN", parser.getText());
					if(isReturnCode)	contents.put("ReturnCode", parser.getText());
					if(isErrMsg)		contents.put("ErrMsg", parser.getText());

					// Body
					if(isTotalFacilifyCount)	contents.put("TotalFacilifyCount", parser.getText());
					if(isTotalPageNumber)		contents.put("TotalPageNumber", parser.getText());
					if(isPageNumber)			contents.put("PageNumber", parser.getText());
					if(isPageFacilityCount)		contents.put("PageFacilityCount", parser.getText());

					// LocalChildFacilityList
					if(isListNumber)	contents.put("ListNumber", parser.getText());
					if(isFacilityName)	contents.put("FacilityName", parser.getText());
					if(isType)			contents.put("Type", parser.getText());
					if(isCertification)	contents.put("Certification", parser.getText());
					if(isSeoul)			contents.put("Seoul", parser.getText());
					if(isFixedNumber)	contents.put("FixedNumber", parser.getText());
					if(isPresentNumber)	contents.put("PresentNumber", parser.getText());
					if(isTelephone)		contents.put("Telephone", parser.getText());
					if(isFax)			contents.put("Fax", parser.getText());
					if(isAddress)		contents.put("Address", parser.getText());
					if(isFacilityID)	contents.put("FacilityID", parser.getText());

					// LocalChildFacilityItemResponse
					if(isCRType)			contents.put("CRType", parser.getText());
					if(isCRSpec)			contents.put("CRSpec", parser.getText());
					if(isPresidentName)		contents.put("PresidentName", parser.getText());
					if(isOpenDate)			contents.put("OpenDate", parser.getText());
					if(isVehicle)			contents.put("Vehicle", parser.getText());
					if(isGovSupport)		contents.put("GovSupport", parser.getText());
					if(isAccidentIns)		contents.put("AccidentInsurance", parser.getText());
					if(isFireIns)			contents.put("FireInsurance", parser.getText());
					if(isCompensationIns)	contents.put("CompensationInsurance", parser.getText());

					LogUtil.log(Log.ERROR, this, "parsedXML()", "contens is null : " + contents);
					// Body - Daum
					if(isChannelTitle)	contents.put("title", parser.getText());
					if(isDescription)	contents.put("description", parser.getText());
					if(isGenerator)		contents.put("generator", parser.getText());
					if(isLink)			contents.put("link", parser.getText());
					if(isLastBuildDate)	contents.put("lastBuildDate", parser.getText());
					if(isTotalCount)	contents.put("totalCount", parser.getText());
					if(isResult)		contents.put("result", parser.getText());

					// item - Daum
					if(isId)				contents.put("id", parser.getText());
					if(isItemTitle)			contents.put("title", parser.getText());
					if(isMountain)			contents.put("mountain", parser.getText());
					if(isLocalName_1)		contents.put("localName_1", parser.getText());
					if(isLocalName_2)		contents.put("localName_2", parser.getText());
					if(isLocalName_3)		contents.put("localName_3", parser.getText());
					if(isMainAddress)		contents.put("mainAddress", parser.getText());
					if(isSubAddress)		contents.put("subAddress", parser.getText());
					if(isBuildingAddress)	contents.put("buildingAddress", parser.getText());
					if(isIsNewAddress)		contents.put("isNewAddress", parser.getText());
					if(isNewAddress)		contents.put("newAddress", parser.getText());
					if(isLng)				contents.put("lng", parser.getText());
					if(isLat)				contents.put("lat", parser.getText());
					if(isPlaceName)			contents.put("placeName", parser.getText());
					if(isPointWx)			contents.put("point_wx", parser.getText());
					if(isPointWy)			contents.put("point_wy", parser.getText());

					LogUtil.log(Log.ERROR, this, "parsedXML()", "Depth : (" + parser.getDepth() + ") TEXT<" + tag + "> : " + parser.getText());
					break;
				case XmlPullParser.END_TAG:
					if(SOAP_HEADER.equals(tag)){
						retDataList.add(contents);
					}else if(SOAP_BODY.equals(tag)){
						retDataList.add(contents);
					}else if(DAUM_CHANNEL.equals(tag)){
						retDataList.add(contents);
					}

					// Header
					if("RequestMsgID".equals(tag))	isRequestMsgID  = false;
					if("ResponseTime".equals(tag))	isResponseTime  = false;
					if("ResponseMsgID".equals(tag))	isResponseMsgID = false;
					if("SuccessYN".equals(tag))		isSuccessYN     = false;
					if("ReturnCode".equals(tag))	isReturnCode    = false; 
					if("ErrMsg".equals(tag))		isErrMsg        = false;

					// Body
					if("TotalFacilifyCount".equals(tag))	isTotalFacilifyCount = false;
					if("TotalPageNumber".equals(tag))		isTotalPageNumber    = false;
					if("PageNumber".equals(tag))			isPageNumber         = false;
					if("PageFacilityCount".equals(tag))		isPageFacilityCount  = false;

					// LocalChildFacilityList
					if("ListNumber".equals(tag))	isListNumber    = false;
					if("FacilityName".equals(tag))	isFacilityName  = false;
					if("Type".equals(tag))			isType          = false;
					if("Certification".equals(tag))	isCertification = false;
					if("Seoul".equals(tag))			isSeoul         = false;
					if("FixedNumber".equals(tag))	isFixedNumber   = false;
					if("PresentNumber".equals(tag))	isPresentNumber = false;
					if("Telephone".equals(tag))		isTelephone     = false;
					if("Fax".equals(tag))			isFax           = false;
					if("Address".equals(tag))		isAddress       = false;
					if("FacilityID".equals(tag))	isFacilityID    = false;

					// LocalChildFacilityItemResponse
					if("CRType".equals(tag))				isCRType          = false;
					if("CRSpec".equals(tag))				isCRSpec          = false;
					if("PresidentName".equals(tag))			isPresidentName   = false;
					if("OpenDate".equals(tag))				isOpenDate        = false;
					if("Vehicle".equals(tag))				isVehicle         = false;
					if("GovSupport".equals(tag))			isGovSupport      = false;
					if("AccidentInsurance".equals(tag))		isAccidentIns     = false;
					if("FireInsurance".equals(tag))			isFireIns         = false;
					if("CompensationInsurance".equals(tag))	isCompensationIns = false;

					// item - Daum
					if("id".equals(tag))				isId              = false;
					if("title".equals(tag))				isItemTitle       = false;
					if("mountain".equals(tag))			isMountain        = false;
					if("localName_1".equals(tag))		isLocalName_1     = false;
					if("localName_2".equals(tag))		isLocalName_2     = false;
					if("localName_3".equals(tag))		isLocalName_3     = false;
					if("mainAddress".equals(tag))		isMainAddress     = false;
					if("subAddress".equals(tag))		isSubAddress      = false;
					if("buildingAddress".equals(tag))	isBuildingAddress = false;
					if("isNewAddress".equals(tag))		isIsNewAddress    = false;
					if("newAddress".equals(tag))		isNewAddress      = false;
					if("lng".equals(tag))				isLng             = false;
					if("lat".equals(tag))				isLat             = false;
					if("placeName".equals(tag))			isPlaceName       = false;
					if("point_wx".equals(tag))			isPointWx         = false;
					if("point_wy".equals(tag))			isPointWy         = false;

					LogUtil.log(Log.ERROR, this, "parsedXML()", "Depth : (" + parser.getDepth() + ") </" + parser.getName() + ">");
					break;
				}

				parserEvent = parser.next();
			}while(parserEvent != XmlPullParser.END_DOCUMENT);

		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		LogUtil.log(Log.ERROR, this, "parsedXML()", "Return Data : " + retDataList);

		return retDataList;
	}

	protected ContentValues parseData(ContentValues content, String tag, XmlPullParser parser){
		if("".equals(tag)){
			content.put("", parser.getText());
		}

		return content;
	}
}