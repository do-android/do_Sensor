package dotest.module.frame.debug;

import core.interfaces.DoILogEngine;

public class DoLogEngine implements DoILogEngine {

	@Override
	public void writeDebug(String _opt) {
		 System.out.println(_opt);
	}

	@Override
	public void writeError(String arg0, Exception _err) {
		 StringBuilder _strB = new StringBuilder();
		_strB.append(_err.getMessage());
		_strB.append("\n");
		_strB.append(_err.getStackTrace());
	        System.out.println(_strB.toString());
	}

	@Override
	public void writePerformance(String _content, int arg1) {
		  System.out.println(_content);
	}

	@Override
	public void writeInfo(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

}
