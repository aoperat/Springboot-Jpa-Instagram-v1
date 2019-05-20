package com.cos.costagram.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.costagram.model.Follow;
import com.cos.costagram.model.Image;
import com.cos.costagram.model.User;
import com.cos.costagram.repository.FollowRepository;
import com.cos.costagram.repository.ImageRepository;
import com.cos.costagram.repository.TagRepository;
import com.cos.costagram.repository.UserRepository;
import com.cos.costagram.service.CustomUserDetails;

@Controller
@RequestMapping("/dummy")
public class DummyController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private TagRepository tagRepository;
	
	@Autowired
	private FollowRepository followRepository;
	
	@PostMapping("/create")
	public @ResponseBody User create(User user) {
		String rawPassword = user.getPassword();
		String encPassword = passwordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		userRepository.save(user);
		return user;
	}
	
	@GetMapping("/images")
	public @ResponseBody List<Image> image(@AuthenticationPrincipal CustomUserDetails userDetail, Model model) {
		System.out.println("Hello EveryOne");
		//1. User (One)
		User user = userDetail.getUser();
		System.out.println("1");
		System.out.println(user.getId());
		//2. Follow:User (Many)
		List<Follow> followList = followRepository.findByFromUserId(user.getId());
		System.out.println("2");
		//3. Follow:Image (Many) 4. Follow:Image:Like(count) (One)
		List<Image> imageList = new ArrayList<>();
		System.out.println("3");
		for(Follow f : followList) {
			List<Image> list = imageRepository.findByUserId(f.getToUser().getId());
			for(Image i : list) {
				imageList.add(i);
			}
			
		}
		System.out.println("4");
		model.addAttribute("imageList", imageList);
		//System.out.println(userDetail.getUsername());
		//System.out.println(userDetail.getUser().getBio());
		//return "/images/image";
		return imageList;
	}
}
