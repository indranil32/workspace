// merge of two sorted list
//def a = [27,38,43];
//def b = [3,9,30,82]
def a = [38,27,43,3,9,82,30];
def start = 0

def merge(a,b) {
    println "merge : "+ a + " "+b
    if (a)
     return b;
    else if (b)
     return a;
    if (a[0] > b[0]) {
        return [b[0],a[0]]
    } else {
        return [a[0],b[0]]
    }
    
}

// divide a list

def divide (a,start,end,str){
    def b = copy(a,start,end)
    println "divide : " +str+" "+start +" "+ end + "  --> "+ b
    if (start>=end) return b;
    int middle = (start+end)/2
    return merge(divide(a,start,middle,"left"), divide(a,middle+1,end,"right"));
}

def copy (a,start,end) {
    b=[]
    int count = 0
    for (int i=start;i<=end;i++) b[count++]=a[i]
    b
}

println divide(a,0,a.size()-1,"start")