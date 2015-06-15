package com.tcl.launcher.interaction.factory;

import com.tcl.launcher.interaction.IDynamicCommandIntercept;
import com.tcl.launcher.interaction.IInteractionRequest;
import com.tcl.launcher.interaction.impl.IDynamicCommandInterceptImpl;
import com.tcl.launcher.interaction.impl.IInteractionRequestImpl;

public class SingInteractionRequestFactory
{
	private static SingInteractionRequestFactory interactionRequestFactory = null;

	private SingInteractionRequestFactory()
	{

	}

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static SingInteractionRequestFactory getInstance()
	{
		if (interactionRequestFactory == null)
		{
			interactionRequestFactory = new SingInteractionRequestFactory();
		}

		return interactionRequestFactory;

	}
	
	public IInteractionRequest getIQuestionServiceImpl()
	{
		return new IInteractionRequestImpl();
	}
	
	public IDynamicCommandIntercept getIDynamicCommandInterceptImpl()
	{
		return new IDynamicCommandInterceptImpl();
	}
}
