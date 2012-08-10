package com.examplerf.shared;
/**
 * Wrapper exception that gets thrown when Objectify get() returns too many results
 */
public class TooManyResultsException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2279521172940075176L;

	public TooManyResultsException()
	{
		super();
	}

	public TooManyResultsException(Throwable t)
	{
		super(t);
	}

	public TooManyResultsException(String msg)
	{
		super(msg);
	}

	public TooManyResultsException(String msg, Throwable t)
	{
		super(msg, t);
	}

}