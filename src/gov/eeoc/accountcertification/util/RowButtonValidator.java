package gov.eeoc.accountcertification.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


@FacesValidator("gov.eeoc.accountcertification.util.RowButtonValidator")
public class RowButtonValidator implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ValidatorException {
		// TODO Auto-generated method stub

		
		System.out.println( " Checking validation ");
	 
	}

}
