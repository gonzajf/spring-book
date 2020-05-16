package com.gonzajf.spring.masteringSpring.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.gonzajf.spring.masteringSpring.form.ProfileForm;
import com.gonzajf.spring.masteringSpring.form.UserProfileSession;
import com.gonzajf.spring.masteringSpring.util.LocalDateFormatter;

@Controller
public class ProfileController {

	@Autowired
	private UserProfileSession userProfileSession;

	@ModelAttribute
	public ProfileForm getProfileForm() {
		return userProfileSession.toForm();
	}

	@RequestMapping("/profile")
	public String profile(ProfileForm form) {
		return "profile/profilePage";
	}

	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public ModelAndView saveProfile(@Valid ProfileForm profileForm, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return new ModelAndView("profile/profilePage");
		}
		
		userProfileSession.saveForm(profileForm);

		System.out.println("save ok" + profileForm);
		return new ModelAndView("redirect:/profile");
	}

	@RequestMapping(value = "/profile", params = { "addTaste" })
	public String addRow(ProfileForm profileForm) {
		profileForm.getTastes().add(null);
		return "profile/profilePage";
	}

	@RequestMapping(value = "/profile", params = { "removeTaste" })
	public String removeRow(ProfileForm profileForm, HttpServletRequest req) {
		Integer rowId = Integer.valueOf(req.getParameter("removeTaste"));
		profileForm.getTastes().remove(rowId.intValue());
		return "profile/profilePage";
	}

	@ModelAttribute("dateFormat")
	public String localeFormat(Locale locale) {
		return LocalDateFormatter.getPattern(locale);
	}
}