package com.trunk.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmartReconcileApplicationTests {

	@Test
	public void contextLoads() {
		Map<String,Object> map = new HashMap();
		map.put("start",0);
		map.put("size",8);
		System.out.println(map.size());
		System.out.println("-----test-------");
	}

}
