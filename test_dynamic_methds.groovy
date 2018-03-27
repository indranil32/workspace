class test_dynamic_methds {

	//def static globalMap = [:];
	def obj = new CommonFunctions();
	def foo = {
			Common.env.sampleMethod()	
			println "extra" 
		}
	static void main(String [] args) {
		println "start"
		//globalMap.Common= new CommonFunctions()
		//globalMap.methodP = globalMap.Common.env
		def tdm = new test_dynamic_methds();
		tdm.myMethod();
		//Common.env.sampleMethod()//newMethod("test");
	}

	def propertyMissing(name) {
		obj
	}

	def myMethod() {
		Common.env.sampleMethod()
		Common.metaClass.extra = foo
		Common.extra "hello";
	}	

}



class EnvironmentMethodProvider {

	public void sampleMethod() {
		println "test successful"
	}

}

class CommonFunctions {

	public EnvironmentMethodProvider env = new EnvironmentMethodProvider();
	
	def propertyMissing(String name) {
		name
	}

	def methodMissing(String name, args) {
		name(*args)
	}

}

