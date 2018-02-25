package com.Tblog.Controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Tblog.Controller.Form.UserUpdateForm;
import com.Tblog.domain.User;
import com.Tblog.Service.UserService;

@Controller
public class FileUploadController {
	@Value("${file.dir}")
	public String ROOT;

	@Autowired
	private UserService userService;

	private final ResourceLoader resourceLoader;

	@Autowired
	public FileUploadController(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	

	// 显示图片
	@GetMapping(value = "/profile/{filename:.+}")
	@ResponseBody
	public ResponseEntity<?> getFile(@PathVariable String filename, HttpSession session) {
		User user = (User) session.getAttribute("CURRENT_USER");
		if (user.getAvatar() == null) {
			return ResponseEntity
					.ok(resourceLoader.getResource("classpath:" + Paths.get("/avatar/", "0.jpg").toString()));
		} else {
			try {
				return ResponseEntity
						.ok(resourceLoader.getResource("file:" + Paths.get(ROOT, filename + ".jpg").toString()));
			} catch (Exception e) {
				return ResponseEntity.notFound().build();
			}
		}
	}

	@GetMapping(value = "/profile")
	public String get(Model model, HttpSession session, @ModelAttribute("message") String message) {
		User user = (User) session.getAttribute("CURRENT_USER");
		model.addAttribute("user", user);
		return "profile";
	}

	@PostMapping(value = "/profile")
	public String handleFileUpload(Model model, @RequestParam(value = "file", required = false) MultipartFile file,
			HttpSession session, RedirectAttributes redirectAttributes, @ModelAttribute("user") @Valid UserUpdateForm form,
			BindingResult result) throws Exception {
		User user = (User) session.getAttribute("CURRENT_USER");
		User OldUser = user;

		// 表单信息出错
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("message", "更新失败");
			session.setAttribute("CURRENT_USER", OldUser);
			return "redirect:/profile";
		}
		User newUser = form.toUser();
		newUser.setId(OldUser.getId());
		newUser.setCode(OldUser.getCode());
		newUser.setPassword(OldUser.getPassword());
		// 保存上传图片
		if (file != null && !file.isEmpty()) {
			String filename = newUser.getId() + ".jpg";
			  //检验目录是否存在
			  File root = new File(ROOT);
			  //判断文件夹是否存在,如果不存在则创建文件夹
			  if (!root.exists()) {
			   root.mkdir();
			  }
			//删除之前的图片
			File f = new File(Paths.get(ROOT, filename).toString());
			if (f.exists()) {
				f.delete();
			}
			Files.copy(file.getInputStream(), Paths.get(ROOT, filename));
			newUser.setAvatar(Paths.get(ROOT, filename).toString());
		} else {
			newUser.setAvatar(OldUser.getAvatar());
		}
		// 获取表单User
		userService.updateUser(newUser);
		// 验证上传表单信息
		redirectAttributes.addFlashAttribute("message", "更新成功");
		session.setAttribute("CURRENT_USER", newUser);
		return "redirect:/profile";
	}
}