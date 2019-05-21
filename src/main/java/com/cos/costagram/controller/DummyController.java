package com.cos.costagram.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
=======
>>>>>>> 0cfe22f36370ad24d853e6968b05cd21a63acc5e
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
<<<<<<< HEAD
=======
import com.cos.costagram.repository.TagRepository;
>>>>>>> 0cfe22f36370ad24d853e6968b05cd21a63acc5e
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
<<<<<<< HEAD
=======
	private TagRepository tagRepository;
	
	@Autowired
>>>>>>> 0cfe22f36370ad24d853e6968b05cd21a63acc5e
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
<<<<<<< HEAD
		
		//1. User (One)
		User user = userDetail.getUser();
		System.out.println("user.getId() : " +user.getId());
		
		//2. User:Follow (List)
		List<Follow> followList = followRepository.findByFromUserId(user.getId());

		//3. User:Follow:Image (List) 4. Follow:Image:Like(count) (One)
		List<Image> imageList = new ArrayList<>();

=======
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
>>>>>>> 0cfe22f36370ad24d853e6968b05cd21a63acc5e
		for(Follow f : followList) {
			List<Image> list = imageRepository.findByUserId(f.getToUser().getId());
			for(Image i : list) {
				imageList.add(i);
<<<<<<< HEAD
			}	
		}
		
		//4. Model에 담아주기
		model.addAttribute("user", user);
		model.addAttribute("imageList", imageList);

		return imageList;
	}
	
	//5개씩 찾는 이미지
	@GetMapping("/image/page")
	public Page<Image> imageList(@PageableDefault(sort= {"id"}, direction = Direction.DESC, size = 5) Pageable pageable){
		Page<Image> list = imageRepository.findAll(pageable);
		return list;
	}
=======
			}
			
		}
		System.out.println("4");
		model.addAttribute("imageList", imageList);
		//System.out.println(userDetail.getUsername());
		//System.out.println(userDetail.getUser().getBio());
		//return "/images/image";
		return imageList;
	}
>>>>>>> 0cfe22f36370ad24d853e6968b05cd21a63acc5e
}
