def a= [3,9,27,30,38,43,82] 
def i=30

def bs(lb,up,a,i) {
    if (lb>=up) { println "Not found!!" ;return }
    int middle = (lb+up)/2
    if (a[middle] == i) {
        println "found at "+middle+" position"
    } else if (a[middle] > i) {
        bs(lb,middle-1,a,i)    
    } else {
        bs(middle+1,up,a,i)    
    }
}

bs(0,7,a,i)