package com.tho.mapprippree;


public class checkInput {
	private String keyString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ_-0123456789";
	private char[] keyCheck = keyString.toCharArray();
	
	public String getKeyString() {
		return keyString;
	}

	public void setKeyString(String keyString) {
		this.keyString = keyString;
	}

	public int strCheck(char[] input){
		int X = 0;
		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < keyCheck.length; j++) {
				if(input[i] == keyCheck[j]){
					//Log.d("Check input name","user = "+input[i]+"   key = "+keyCheck[j]+"  Round i = "+i+" Roundin j = "+j);
					X++;
					break;
				}										
			}
		}
		return X;
	}
	
	
}
