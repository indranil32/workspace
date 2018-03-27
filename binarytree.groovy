a = [6,5,3,1,8,7,2,4]

//println a
for (int i = 0 ; i < a.size();i++) {
    int ele = a[i] // 3
    int pos = i;
    for (int j = i-1 ; j >= 0; j--) {
        // 3 < 5
        if (ele < a[j]) {
             pos = j;
             continue;
        }
    }
    move(a,pos,i)     
    a[pos] = ele;
    println a
}

println a
// a 0 1
// a 0 2
def move(a,j,i) {
    for (int m = i ; m >= j;m--) {
        a[m] = a[m-1]
    }
}