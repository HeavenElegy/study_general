package com.heaven.elegy.jmeter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.testelement.property.JMeterProperty;
import org.apache.jmeter.testelement.property.PropertyIterator;
import org.apache.jmeter.testelement.property.TestElementProperty;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 这是用于JMeter的BeanShell脚本类。
 * <p>其中，所有位置不能出现泛型</p>
 * <p>同包内的类必须使用<code>import</code>进行引用</p>
 * <p>不建议使用groovy。因为BeanShell脚本类的每个语句需要使用";"结束。并且数组声明方式服务不符合java规范。无法进行编译</p>
 * @author li.xiaoxi
 * @date 2019/06/06 10:23
 */
public class Test01 extends BaseScript {


	@Override
	void exec() {
		try{
			// 构造json请求
			ObjectMapper om = new ObjectMapper();
			Map params = new HashMap();
			Arguments arguments = sampler.getArguments();

			printIterator(vars.getIterator());

			PropertyIterator iterator = arguments.iterator();
			while (iterator.hasNext()) {
				JMeterProperty property = iterator.next();
				String key = property.getName();
				String value;

				log.info("[{}: {}]", property.getName(), property.getStringValue());

				if(property instanceof TestElementProperty) {
					value = ((TestElementProperty)property).getElement().getPropertyAsString("Argument.value");
				}else {
					log.warn("param is continue. [key:{}, val:{}]", key, property.getStringValue());
					continue;
				}

				if(key == null || key.length() == 0) {
					log.warn("param is continue. [key:{}, val:{}]", key, property.getStringValue());
					continue;
				}

				if(value.indexOf("$") != 0) {
					iterator.remove();
				}

				params.put(key, value);

			}

			String json = om.writeValueAsString(params);
			log.info("json:{}", json);
			vars.put("json", json);

			// 删除原数据结构中的参数
			sampler.setArguments(arguments);


			printIterator(vars.getIterator());


		}catch (Throwable e) {
			log.error("{}", e);
		}
	}

	public static void main(String[] args) {
		Test01 test01 = new Test01();
		test01.exec();
	}


	public void printIterator(Iterator iterator) {
		log.info("------------------ print start ------------------");
		iterator.forEachRemaining(new Consumer() {
			@Override
			public void accept(Object stringObjectEntry) {
				log.info("{}", stringObjectEntry);
			}
		});
		log.info("------------------ print end ------------------");
	}
}
