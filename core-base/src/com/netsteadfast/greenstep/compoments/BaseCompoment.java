/* 
 * Copyright 2012-2016 bambooCORE, greenstep of copyright Chen Xin Nien
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * -----------------------------------------------------------------------
 * 
 * author: 	Chen Xin Nien
 * contact: chen.xin.nien@gmail.com
 * 
 */
package com.netsteadfast.greenstep.compoments;

import com.netsteadfast.greenstep.util.TemplateUtils;

public abstract class BaseCompoment<T extends CompomentsModel> implements IBaseCompoment<T> {
	private static final long serialVersionUID = -7898543746781521682L;
	private T model;
	
	public T getModel() {
		return model;
	}
	
	public void setModel(T model) {
		this.model = model;
	}
	
	public void doRender() throws Exception {
		if (null == this.model) {
			throw new Exception("model is null!");
		}
		this.getModel().getParameters().put("height", this.getModel().getHeight());
		this.getModel().getParameters().put("width", this.getModel().getWidth());
		this.model.setContent( TemplateUtils.processTemplate(
				"resourceTemplate", 
				BaseCompoment.class.getClassLoader(), 
				this.model.getResource(), 
				this.model.getParameters()) );
	}
	
}
