a = [6,5,3,1,8,7,2,4]

//a = [3,9,1,7,4,6,5,8,2]
println a
for (int i = 0 ; i < a.size() ; i++) {
    int smallest = a[i]
    int pos = i
    for (int j = 0 ; j < i; j++) {
        if (smallest < a[j]){
            pos = j
            break;
        }
    }    
    if (pos != i) {        
        move(pos,i)        
        a[pos] = smallest;
    }
    println a
}

def move(pos, i) {
    for (int m = i ; m > pos ; m--) {
        a[m] = a[m-1]
    }
}
