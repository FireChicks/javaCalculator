/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package calc;

import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author LeeJanyun
 */
public class StackCalc {
    private Stack<String> stack = new Stack<>();
    private ArrayList<String> postFix = new ArrayList<String>();
    
    public void pushMathExp(String mathExp) {
        String pattern = "[^0-9|.|%|]+";  
        String pattern2 = "[#|##|]+";
        String preOperator = "";
        postFix = new ArrayList<String>();
        
        String[] splitMathExp = mathExp.split(pattern2);
        
        for(int i = 0; i < splitMathExp.length; i++) {
            if(splitMathExp[i].equals("")) {
                continue;
            }
            if(splitMathExp[i].matches(pattern)) {
                if(stack.isEmpty()) {
                    stack.push(splitMathExp[i]); 
                    preOperator = splitMathExp[i];
                } else if(splitMathExp[i].equals("(")) {
                     stack.push(splitMathExp[i]);
                } else if(splitMathExp[i].equals(")")) {
                    while(!stack.peek().equals("(")){
                        postFix.add( stack.pop());
                     }
                    stack.pop();
                } else if (checkPriority(splitMathExp[i]) > checkPriority(stack.peek())) {
                    stack.push(splitMathExp[i]);
                } else if (checkPriority(splitMathExp[i]) == checkPriority(stack.peek())){
                    postFix.add(stack.pop());
                    stack.push(splitMathExp[i]);
                } else {
                    postFix.add(splitMathExp[i]);
                }
            } else {
                if(splitMathExp[i].charAt(splitMathExp[i].length() -1) == '%') {
                    splitMathExp[i] = splitMathExp[i].substring(0, splitMathExp[i].length() - 1);
                    splitMathExp[i] = cal(100.0, Double.parseDouble( splitMathExp[i]),  "/");
                }
                postFix.add(splitMathExp[i]);
            }
        }
        
        while(!stack.isEmpty()){
            postFix.add( stack.pop());
        }
        
        
        for(int i = 0; i < postFix.size(); i ++){
            if(postFix.get(i).matches(pattern)) {
                stack.push(cal(Double.parseDouble(stack.pop()),Double.parseDouble(stack.pop()), postFix.get(i)));
            } else{
                stack.push(postFix.get(i));
            }
        }            
        
    }
    
    public String popMathExp() {       
        return stack.pop();
    }
    
    public int checkPriority(String operator) {
        if(operator.equals("+") || operator.equals("-")) {
            return 1;
        } else if (operator.equals("*") || operator.equals("/")) {
            return 2;
        }
        return 0;
    }
    
    public String cal(double operand1,double operand2, String operator) {
        Double tempResult;
        if(operator.equals("+")) {
            tempResult = operand1 + operand2;
            return Double.toString(tempResult);
        } else if (operator.equals("-")) {
            tempResult = operand2 - operand1;
            return Double.toString(tempResult);
        } else if (operator.equals("*")) {
            tempResult = operand1 * operand2;
            return Double.toString(tempResult);
        } else if (operator.equals("/")) {
            tempResult = operand2 / operand1;
            tempResult = Math.round(tempResult*100)/100.0;
            String result = String.format("%.3f", tempResult);
            return result;
        } else {
            return "";
        }   
    }
}
          
