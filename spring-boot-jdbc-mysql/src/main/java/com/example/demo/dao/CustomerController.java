package com.example.demo.dao;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.ResourceAccessException;

import com.example.demo.model.Customer;

@Controller   
	@RequestMapping(path="/demo") 
	public class CustomerController {
		@Autowired 
		private CustomerRepository customerRepository;

		@GetMapping(path="/add") // Map ONLY GET Requests
		public @ResponseBody String addNewUser (@RequestParam String name
				, @RequestParam String email) {
			// @ResponseBody means the returned String is the response, not a view name
			// @RequestParam means it is a parameter from the GET or POST request

			Customer n = new Customer();
			n.setName(name);
			n.setEmail(email);
			customerRepository.save(n);
			return "Saved";
		}

		@GetMapping(path="/all")
		public @ResponseBody Iterable<Customer> getAllUsers() {
			// This returns a JSON or XML with the users
			return customerRepository.findAll();
		}
		
		@PostMapping("/notes")
		public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) throws URISyntaxException {
			Customer result=  customerRepository.save(customer);
		     return ResponseEntity.created(new URI("/demo/notes/" + result.getId())).body(result);
				
		}
		
		@PutMapping("/notes/{id}")
		public ResponseEntity<Customer> updateNote(@PathVariable(value = "id") int noteId,
                @Valid @RequestBody Customer customerDetails) throws URISyntaxException {


			try {
			Customer customer = customerRepository.findById(noteId)
			.orElseThrow(() -> new ResourceAccessException("Customer"));
			
			customer.setName(customerDetails.getName());
			customer.setEmail(customerDetails.getEmail());
			
			Customer updatedNote = customerRepository.save(customer);
			//return updatedNote;
			
				return ResponseEntity.created(new URI("/demo/notes/" + updatedNote.getId())).body(updatedNote);
			}
			catch (EntityNotFoundException e) {
				return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);

			}
	}
		
		@DeleteMapping("/notes/{id}")
		public ResponseEntity<?> deleteNote(@PathVariable(value = "id") int noteId) {
			Customer customer = customerRepository.findById(noteId)
		            .orElseThrow(() -> new ResourceAccessException("Customer"));

			customerRepository.delete(customer);

		    return ResponseEntity.ok().build();
		}
		
		@GetMapping(path="/all/{id}")
		public @ResponseBody Optional<Customer> getSpecificUsers(@PathVariable(value = "id") int noteId) {
			// This returns a JSON or XML with the users
			return customerRepository.findById(noteId);
		}
		
}