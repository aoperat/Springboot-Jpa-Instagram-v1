package com.cos.costagram.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cos.costagram.model.Image;
import com.cos.costagram.model.Tag;
import com.cos.costagram.model.User;
import com.cos.costagram.repository.ImageRepository;
import com.cos.costagram.repository.TagRepository;
import com.cos.costagram.service.CustomUserDetails;
import com.cos.costagram.util.UtilCos;

@Controller
public class ImageContoller {
	
	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private TagRepository tagRepository;
	
	@GetMapping("/home")
	public String home() {
		return "home";
	}
	
	@GetMapping("/images")
	public String image(@AuthenticationPrincipal CustomUserDetails userDetail) {
		//System.out.println(userDetail.getUsername());
		//System.out.println(userDetail.getUser().getBio());
		return "/images/image";
	}
	
	@PostMapping("/image/upload")
	public @ResponseBody Image imageUpload(
			@AuthenticationPrincipal CustomUserDetails userDetail,
			@RequestParam("file") MultipartFile file, 
			String caption, 
			String location, 
			String tags) throws IOException {
		Path filePath = Paths.get(UtilCos.getResourcePath()+file.getOriginalFilename());
		
		Files.write(filePath, file.getBytes());
		User user = userDetail.getUser();

		List<String> tagList = UtilCos.tagParser(tags);
		
		Image image = Image.builder()
				.caption(caption)
				.location(location)
				.user(user)
				.mimeType(file.getContentType())
				.fileName(file.getOriginalFilename())
				.filePath("/image/"+file.getOriginalFilename())
				.build();
		
		imageRepository.save(image);
		
		for(String t : tagList) {
			Tag tag = new Tag();
			tag.setName(t);
			tag.setImage(image);
			tag.setUser(user);
			tagRepository.save(tag);
			image.getTags().add(tag); //DB에 영향을 미치지 않음.
		}
		
		return image;
	}
}
