/*
 * Copyright (C) 2014, United States Government, as represented by the
 * Administrator of the National Aeronautics and Space Administration.
 * All rights reserved.
 *
 * Symbolic Pathfinder (jpf-symbc) is licensed under the Apache License, 
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0. 
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */
package gov.nasa.jpf.symbc.bytecode;

import gov.nasa.jpf.symbc.numeric.*;
import gov.nasa.jpf.vm.Instruction;
import gov.nasa.jpf.vm.StackFrame;
import gov.nasa.jpf.vm.ThreadInfo;


/**
 * Convert int to character
 * ..., value => ..., result
 */
public class I2C extends gov.nasa.jpf.jvm.bytecode.I2C {
 
  @Override
  public Instruction execute (ThreadInfo th) {
	  StackFrame sf = th.getModifiableTopFrame();
	  Expression sym_val = (Expression) sf.getOperandAttr();
	  
	  Instruction result = super.execute(th);
	  // Java Ranger change begins to address wrong answer given for SV-COMP jbmc-regression/cast1 benchmark
	  if (sym_val instanceof IntegerExpression) {
	  	sym_val = new BinaryLinearIntegerExpression((IntegerExpression) sym_val, Operator.AND, new IntegerConstant(65535));
	  }
	  // Java Ranger change ends
	  //DL:TBD: I think we should add a choice here to account for possible truncation.
	  if (sym_val != null)
		  sf.setOperandAttr(sym_val);
	  return result;
  }

}
