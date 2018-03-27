Stack stack = new Stack();

def str = "I cannot do it, you know"
println str.trim();
def l = str.split(" ").length
for (int i = 0 ; i < l; i++) {
    stack.push(str.split(" ")[i])
}
str = ""
for (int i = 0 ; i < l; i++) {
    str += (stack.empty() ? "":stack.pop()) + " ";
}

println str.trim();
    
    