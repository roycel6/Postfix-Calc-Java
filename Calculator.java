package assignment2;

import java.util.Stack;

public class Calculator {
	public static void main(String[] args) {
		
		String s = "10 * (2 + 15) / 17";
		System.out.println("In-fix expression: " + s);
		s = postFix(s);
		System.out.println("Post-fix expression: " + s);
		System.out.println("Result: " + Calculation(s));
	}
	
	public static String postFix(String s) {	//converts in-fix to post-fix
		Stack <Character> st = new Stack<Character>();	//creates a stack to store operators
		String postfix = "";
		char ch[] = s.toCharArray();		//converts string to char array
		
		for(int i = 0; i < ch.length; i++) {	//goes through char array
			if(ch[i] != '+' && ch[i] != '-' && ch[i] != '*' && ch[i] != '/' && ch[i] != '(' && ch[i] != ')') {
				postfix = postfix + ch[i];		//operands are added to post-fix
			}
			else if(ch[i] == '(') {	//parentheses are prioritized over other operands and are pushed first
				st.push(ch[i]);
			}
			else if(ch[i] == ')') {		//everything between open and close parentheses are pushed in
				while (!st.isEmpty()) {
					char t = st.pop();
					if(t != '(') {
						postfix = postfix + t;
					}
					else {
						break;
					}
				}
			}
			else if(ch[i] == '+' || ch[i] == '-' || ch[i] == '*' || ch[i] == '/') {
				if(st.isEmpty()) {		//if the string is empty, these operands are pushed
					st.push(ch[i]);
				}
				else {
					while(!st.isEmpty()) {	
						char t = st.pop();
						if(t == '(') {		//if parenthesis is popped, it is pushed back in
							st.push(t);
							break;
						}
						else if(t == '+' || t == '-' || t == '*' || t == '/') {		//if an operand is popped,
							if(getPriority (t) < getPriority(ch[i])) {	//priority is checked to determine order of push
								st.push(t);	//if t is lower priority, it is pushed
								break;
							}
							else {
								postfix = postfix + t;	//if t is higher priority, it is added to post-fix
							}
						}
					}
					st.push(ch[i]);		//lower priority is pushed
				}
			}
		}
		while(!st.isEmpty()) {		//remaining operands in stack are popped
			postfix = postfix + st.pop();
		}
		return postfix;
	}
	
	public static int getPriority(char c) {
		if(c == '+' || c== '-') {		//"+" and "-" are lower priority than "*" and "/"
			return 1;
		}
		else {		//"*" and "/" return a higher priority
			return 2;
		}
	}

	public static int Calculation(String s) {
		Stack<Integer> st = new Stack<Integer>();		//creates new stack to calculate
		int x = 0;
		int y = 0;
		int flag = 0;
		int temp = 0;
		int tempResult = 0;
		char ch[] = s.toCharArray();
		
		for(int i = 0; i < ch.length; i++) {		
			if(ch[i] >= '0'&& ch[i] <= '9') {		//if character is a digit
				if(flag == 1) {		//this if statement is used to account for double digit inputs
					temp = st.pop();	//digit at top of stack is popped
					tempResult = temp * 10 + (ch[i] - '0'); //turns digit back to its original double digit input
					st.push(tempResult);	//final result pushed to stack
				}
				else if(flag == 0) {
					st.push(ch[i] - '0');	//pushes if input is single digit
					flag = 1;
				}
			}
			else if(ch[i] == ' ') {	//blank space is used as separator to differentiate single and double digit numbers
				flag = 0;			
			}
			else {
				flag = 0;
				y = st.pop();	//top of stack is popped
				x = st.pop();	//next element in stack is popped
				switch(ch[i]) {
				case '+':
					st.push(x + y);		//adds if there's addition sign and pushes result
					break;
				case'-':
					st.push(x - y);		//subtracts if there's subtraction sign and pushes result
					break;
				case '*':
					st.push(x * y);		//multiplies if there's multiplication sign and pushes result
					break;
				case '/':
					st.push(x / y);		//divides if there's division sign and pushes result
					break;
				}
			}
		}
	
		return st.pop();		//returns final result
	}

}
/*Time complexity: O(n)
Space complexity: O(n) */

/*Problem 2: I don't think using a deque will really improve time and space complexity. Since deque supports element
 insertion and removal at both ends, perhaps elements will be able to be accessed more easily. However,
 time and space will probably remain linear. Deque or stack are both sufficient in calculation.*/

/*Test cases:
 if a closing parenthesis is missing: 
 
 char search = ')';
 int flag = 0;
 for(int i = 0; i < ch.length; i++){
 	if(ch[i] == search){
 	flag = 1;
 	break;
 	}
 }
 if (flag == 0){
 System.out.println("Please enter closing parenthesis");
 }
 
 if input in not valid (not operand or operator, or operand as first character of input string):
 
boolean flag = false;
while(!flag) {
try {			
	s = postFix(s);
	Calculation(s);
	flag = true;
	break;
}catch(EmptyStackException e) {
	System.out.print("Please enter a valid input: ");
	continue;
}
 
*/
