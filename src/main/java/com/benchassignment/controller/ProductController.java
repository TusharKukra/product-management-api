package com.benchassignment.controller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.benchassignment.models.Product;
import com.benchassignment.models.ProductDto;
import com.benchassignment.repository.ProductRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	@GetMapping({ "", "/" })
	@Cacheable(value = "productsCache", key = "'productList'")
	public String showProductList(Model model) {
		List<Product> products = productRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
		model.addAttribute("products", products);
		return "products/index";
	}

	@GetMapping("/create")
	public String showCreatePage(Model model) {
		ProductDto productDto = new ProductDto();
		model.addAttribute("productDto", productDto);
		return "products/createproduct";
	}

	@PostMapping("/create")
	public String createProduct(@Valid @ModelAttribute ProductDto productDto, BindingResult result) {

		if (productDto.getImageFile().isEmpty()) {
			result.addError(new FieldError("productDto", "imageFile", "This image file is required"));
		}

		if (result.hasErrors()) {
			return "products/createproduct";
		}

		// save image file (new)
		MultipartFile image = productDto.getImageFile();
		Date createdAt = new Date();
		String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();
		try {
			String uploadDirectory = "public/images/";
			Path uploadPath = Paths.get(uploadDirectory);
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			try (InputStream inputStream = image.getInputStream()) {
				Files.copy(inputStream, Paths.get(uploadDirectory + storageFileName),
						StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (Exception exception) {
			System.out.println("Exception: " + exception.getMessage());
		}

		Product product = new Product();
		product.setName(productDto.getName());
		product.setBrand(productDto.getBrand());
		product.setCategory(productDto.getCategory());
		product.setPrice(productDto.getPrice());
		product.setDescription(productDto.getDescription());
		product.setCreatedAt(createdAt);
		product.setImageFileName(storageFileName);

		productRepository.save(product);

		return "redirect:/products";
	}

	@GetMapping("/edit")
	public String showEditPage(Model model, @RequestParam Integer id) {
		try {
			Product product = productRepository.findById(id).get();
			model.addAttribute("product", product);

			ProductDto productDto = new ProductDto();
			productDto.setName(product.getName());
			productDto.setBrand(product.getBrand());
			productDto.setCategory(product.getCategory());
			productDto.setPrice(product.getPrice());
			productDto.setDescription(product.getDescription());
			model.addAttribute("productDto", productDto);

		} catch (Exception exception) {
			System.out.println("Exception: " + exception.getMessage());
			return "redirect:/products";
		}
		return "products/editproduct";
	}

	@PostMapping("/edit")
	public String updateProduct(Model model, @RequestParam Integer id, @Valid @ModelAttribute ProductDto productDto,
			BindingResult result) {
		
		try {
			Product product = productRepository.findById(id).get();
			model.addAttribute("product", product);
			if(result.hasErrors()) {
				return "products/editproduct";
			}
			
			if(!productDto.getImageFile().isEmpty()) {
			
				// deleting old image file
				String uploadDirectory = "public/images/";
				Path oldImagePath = Paths.get(uploadDirectory + product.getImageFileName());
				
				try {
					Files.delete(oldImagePath);
				} catch (Exception exception) {
					System.out.println("Exception: " + exception.getMessage());
				}
				
				// saving new image file
				MultipartFile image = productDto.getImageFile();
				Date createdAt = new Date();
				String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();
				
				try (InputStream inputStream = image.getInputStream()) {
					Files.copy(inputStream, Paths.get(uploadDirectory + storageFileName),
							StandardCopyOption.REPLACE_EXISTING);
				}
				product.setImageFileName(storageFileName);
			}
			
			product.setName(productDto.getName());
			product.setBrand(productDto.getBrand());
			product.setCategory(productDto.getCategory());
			product.setPrice(productDto.getPrice());
			product.setDescription(productDto.getDescription());
			
			productRepository.save(product);
			
		} catch (Exception exception) {
			System.out.println("Exception: " + exception.getMessage());
		}
		
		return "redirect:/products";
	}
	
	@GetMapping("/delete")
	public String deleteProduct (@RequestParam Integer id) {
		
		try {
			Product product = productRepository.findById(id).get();
			
			// delete product image also
			Path imagePath = Paths.get("public/images/" + product.getImageFileName());
			try {
				Files.delete(imagePath);
			} catch (Exception exception) {
				System.out.println("Exception while deleting product image : " + exception.getMessage());
			}
			
			// delete the product
			productRepository.delete(product);
			
		} catch (Exception exception) {
			System.out.println("Exception while deleting product : " + exception.getMessage());
		}
		
		return "redirect:/products";
	}
	
	
}
