a = [6,5,3,1,8,7,2,4]
println a
for (int i = 0 ; i < a.size();i++) {
    def smallest = a[i]
    def pos = i
    for (int j = i+1 ; j < a.size();j++) {
        if (smallest > a[j]) {
            smallest = a[j]
            pos = j
        }
    }
    if (pos != i) {
        move(a,i,pos)
        a[i] = smallest
    }
    println a
}

def move(a,i,j) {
    for (int m = j;m > i;m--){
        a[m] = a[m-1]
    }
}