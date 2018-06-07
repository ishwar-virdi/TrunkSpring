package com.trunk.demo;

import com.trunk.demo.Util.CalenderUtil;
import com.trunk.demo.bo.BankStmtBO;
import com.trunk.demo.bo.Impl.RedisBOImpl;
import com.trunk.demo.bo.ReconcileResultBO;
import com.trunk.demo.bo.TokenBO;
import com.trunk.demo.bo.UserBO;
import com.trunk.demo.model.mongo.BankStmt;
import com.trunk.demo.model.mongo.ReconcileResult;
import com.trunk.demo.model.mongo.User;
import com.trunk.demo.repository.BankStmtRepository;
import com.trunk.demo.repository.ResultsRepository;
import com.trunk.demo.repository.UsersRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SmartReconcileApplicationTests {

	@Autowired
	private TokenBO tokenBO;
	@Autowired
	private BankStmtRepository bankStmtRepository;
	@Autowired
	private BankStmtBO bankStmtBO;

	@Autowired
	private UserBO userBO;
	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private ReconcileResultBO reconcileResultBO;
	@Autowired
	private ResultsRepository resultsRepository;

	@Autowired
	private RedisBOImpl redisBO;

	private UtilTests utilTests;

	@Before
	public void setUp(){
		utilTests = new UtilTests();
	}

	@Test
	public void BCrypt() {
		utilTests.encrypt();
		utilTests.isEquals();
	}

	@Test
	public void calenderUtil(){
		utilTests.isMonth();
		utilTests.getDateYear();
		utilTests.getDateMonth();
		utilTests.getDateDay();
		utilTests.EndDayOfMonthByString();
	}

	@Test
	public void Token(){
		String token = tokenBO.generateToken();
		String getToken = tokenBO.getToken().toString();
		if(!token.equals(getToken)){
			System.out.println("----------------getToken not Pass----------------");
		}
		if(!token.equals(getToken)){
			System.out.println("----------------getToken not Pass----------------");
		}
		if(tokenBO.destroyToken()){
			Object destroyToken = tokenBO.getToken();
			if(destroyToken != null){
				System.out.println("------------destroyToken not Pass------------");
			}
		}
	}


	@Test
	public void bankStmt(){
		CalenderUtil calenderUtil = new CalenderUtil();
		Date date = calenderUtil.getFristDayOfMonth(1950,2);
		Date start = calenderUtil.getFristDayOfMonth(1950,1);
		Date end = calenderUtil.getFristDayOfMonth(1950,3);
		BankStmt bankStmtOne = new BankStmt(1,new Date(),"accountDescription",1231,"AUD",
				date,"transactionDescription",0,1.1,2.2);
		bankStmtBO.insert(bankStmtOne);
		BankStmt bankStmtTwo = new BankStmt(2,new Date(),"accountDescription",1231,"AUD",
				date,"transactionDescription",0,1.1,2.2);
		bankStmtBO.insert(bankStmtTwo);

		List<BankStmt> list = bankStmtBO.findAllBetweenDates(start,end);
		if(list.size() != 2){
			System.out.println("--------findAllBetweenDates not Pass------------");
		}

		Optional<BankStmt> bankStmt = bankStmtBO.findById(1);
		if(!bankStmt.isPresent()){
			System.out.println("--------findById not Pass-----------------------");
		}
	}



	String uid = "";
	@Test
	public void user(){
		User user = new User("test","test");
		userBO.save(user);

		User users =userBO.findByUsername("test").get(0);
		if(users == null){
			System.out.println("-----User findByUsername not Pass----------------");
		}
		uid = users.getId();
		User userId = userBO.findById(uid);
		if(userId == null){
			System.out.println("-----User findById not Pass-----------------------");
		}
	}


	@Test
	public void result(){
		CalenderUtil calenderUtil = new CalenderUtil();
		Date start = calenderUtil.getFristDayOfMonth(1950,1);
		Date end = calenderUtil.getFristDayOfMonth(1950,3);
		ReconcileResult result = new ReconcileResult("Feb-1950","userId",7,10,
				calenderUtil.getFristDayOfMonth(1950,2),
				calenderUtil.getLastDayOfMonth(1950,2));
		reconcileResultBO.save(result);
		List<ReconcileResult> resultUserId = reconcileResultBO.findAllByUserId("userId");
		if(resultUserId.size() != 1){
			System.out.println("-----resultUserId not Pass-----------------------");
		}
		ReconcileResult resultById = reconcileResultBO.findById("Feb-1950");
		if(resultById == null){
			System.out.println("-----resultFindById not Pass-----------------------");
		}
		List<ReconcileResult> resultDate = reconcileResultBO.findByUserIdAndStartDateGreaterThanEqualAndEndDateLessThan("userId",start,end);
		if(resultDate.size() != 1){
			System.out.println("-----resultDateRange not Pass-----------------------");
		}

		reconcileResultBO.updateIncreaseIsReconcile(1,result);
		ReconcileResult resultIsReconcile = reconcileResultBO.findById("Feb-1950");
		if(resultIsReconcile.getIsReconciled() != 8 ||
				resultIsReconcile.getNotReconciled() != 9){
			System.out.println("-----IncreaseIsReconcile not Pass-----------------------");
		}


		reconcileResultBO.updateIncreaseNotReconcile(1,result);
		ReconcileResult resultNotReconcile = reconcileResultBO.findById("Feb-1950");
		if(resultNotReconcile.getIsReconciled() != 7 ||
				resultNotReconcile.getNotReconciled() != 10){
			System.out.println("-----IncreaseNotReconcile not Pass----------------------");
		}
	}

	@Test
	public void redis(){
		redisBO.pushFileName("file");
		redisBO.pushType("type");
		if(!"file".equals(redisBO.getFileName())){
			System.out.println("-----redisBO.getFileName() not Pass----------------------");
		}
		if(!"type".equals(redisBO.getType())){
			System.out.println("-----redisBO.getType() not Pass----------------------");
		}

		redisBO.deleteCache();
		if(redisBO.getType() != null){
			System.out.println("-----redisBO.deleteCache() not Pass----------------------");
		}
	}
	@After
	public void cleanUp(){
		bankStmtRepository.deleteById(1);
		bankStmtRepository.deleteById(2);

		usersRepository.deleteById(uid);
		resultsRepository.deleteById("Feb-1950");
	}
}
