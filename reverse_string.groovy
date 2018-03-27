//def str = "him her them"
def str = "I cannot do it, you know"
def  l = str.split(" ").length
def tmp = str.split(" ")[l-1]
println str
for (int i = l-2 ;i >= 0 ;i--) {
    def tmp2 = str.split(" ")[i].trim();
    str = str.substring(0, str.lastIndexOf(tmp2)).trim() + " " +tmp + " "+ tmp2;
    //println str
    tmp += " " + tmp2;
    //println tmp
    
}
println str.trim()