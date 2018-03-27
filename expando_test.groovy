//ExpandoMetaClass.enableGlobally()

class TestClass {

    def test1() {
        println "test"
    }
}



def map = [xyz: new TestClass()]

//test1("me")

def propertyMissing(name) {
    new TestClass()
}

//def methodMissing(String name, args) {
//    t."${name}"(args)
//}

abc.test1()