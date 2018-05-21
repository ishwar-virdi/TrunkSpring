package com.trunk.demo.service.Impl;

import com.google.gson.Gson;
import com.trunk.demo.model.mongo.ReconcileResult;
import com.trunk.demo.repository.ResultsRepository;
import com.trunk.demo.service.SearchService;
import com.trunk.demo.vo.ListReconcileResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private ResultsRepository resultsRepository;

	@Autowired
	private Gson gson;

	@Override
	public String resultSearch(String userId, String value) {
		String json = "";
		ListReconcileResultVO results;
		int lessSignIndex = value.indexOf("<");
		int largeSignIndex = value.indexOf(">");
		int hyphenIndex = value.indexOf("-");
		int slashIndex = value.indexOf("/");
		int secondSlash = value.indexOf("/", slashIndex + 1);
		try {
			// date
			if (value.length() >= 10 && secondSlash - slashIndex == 3 && hyphenIndex == -1) {
				Date date = stringToDate(value);
				Date nextDate = getNextDate(value);
				results = new ListReconcileResultVO(
						resultsRepository.findByUserIdAndLastModifiedBetween(userId, date, nextDate));
				json = gson.toJson(results.getList());
			}
			// dateRange
			else if (value.length() >= 21 && secondSlash - slashIndex == 3 && hyphenIndex != -1) {
				String dates[] = value.split("-");
				Date startDate = stringToDate(dates[0]);
				Date endDate = getNextDate(dates[1]);
				results = new ListReconcileResultVO(resultsRepository
						.findByUserIdAndStartDateGreaterThanEqualAndEndDateLessThan(userId, startDate, endDate));
				json = gson.toJson(results.getList());
			}
			// percentage
			else if (value.length() <= 3 && largeSignIndex == -1 && lessSignIndex == -1) {
				int percentage = Integer.parseInt(value);
				if (percentage > 100 || percentage < 0) {
					return "fail";
				}
				results = new ListReconcileResultVO(resultsRepository.findByUserIdAndPercentage(userId, percentage));
				json = gson.toJson(results.getList());
			}
			// Retrieve percentage by greater than
			else if (value.length() <= 4 && largeSignIndex != -1 && lessSignIndex == -1) {
				int percentage = Integer.parseInt(value.replace(">", ""));
				if (percentage > 100 || percentage < 0) {
					return "fail";
				}
				results = new ListReconcileResultVO(
						resultsRepository.findByUserIdAndPercentageGreaterThanEqual(userId, percentage));
				json = gson.toJson(results.getList());
			}
			// Retrieve percentage by less than
			else if (value.length() <= 4 && largeSignIndex == -1 && lessSignIndex != -1) {
				int percentage = Integer.parseInt(value.replace("<", ""));
				if (percentage > 100 || percentage < 0) {
					return "fail";
				}
				results = new ListReconcileResultVO(
						resultsRepository.findByUserIdAndPercentageLessThanEqual(userId, percentage));
				json = gson.toJson(results.getList());
			}
			// Retrieve percentage by greater than and less than
			else if (value.length() <= 8 && largeSignIndex != -1 && lessSignIndex != -1) {
				int lessThanValue = 0;
				int largerThanValue = 0;

				if (largeSignIndex > lessSignIndex) {
					lessThanValue = Integer.parseInt(value.substring(lessSignIndex + 1, largeSignIndex));
					largerThanValue = Integer.parseInt(value.substring(largeSignIndex + 1));
				} else {
					lessThanValue = Integer.parseInt(value.substring(lessSignIndex + 1));
					largerThanValue = Integer.parseInt(value.substring(largeSignIndex + 1, lessSignIndex));
				}
				if (largerThanValue > lessThanValue || lessThanValue > 100 || largerThanValue < 0) {
					return "fail";
				}
				lessThanValue++;
				largerThanValue--;
				results = new ListReconcileResultVO(
						resultsRepository.findByUserIdAndPercentageBetween(userId, largerThanValue, lessThanValue));
				json = gson.toJson(results.getList());
			}
			// Retrieve by ID
			else if (value.length() == 24) {
				Optional<ReconcileResult> id = resultsRepository.findById(value);
				if (!id.isPresent()) {
					return "fail";
				}
				List<ReconcileResult> result = new ArrayList<>();
				result.add(id.get());
				json = gson.toJson(result);
			} else {
				return "fail";
			}
		} catch (Exception e) {
			return "fail";
		}
		return json;
	}

	@Override
	public String detailSearch(String userId, String value) {
		return "aa";
	}

	private Date stringToDate(String s) throws Exception {
		s = s.substring(0, 10);
		s = s.replaceAll(" ", "");
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		return sdf.parse(s);
	}

	private Date getNextDate(String s) throws Exception {
		s = s.replaceAll(" ", "");
		StringBuffer sb = new StringBuffer(s);
		int day = Integer.parseInt(sb.substring(3, 5)) + 1;
		StringBuffer nextDay = new StringBuffer();
		if (day < 10) {
			nextDay.append("0");
		}
		nextDay.append(String.valueOf(day));
		sb.replace(3, 5, nextDay.toString());
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		date = sdf.parse(sb.toString());
		return date;
	}

}
