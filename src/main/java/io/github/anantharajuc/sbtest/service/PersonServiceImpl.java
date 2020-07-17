package io.github.anantharajuc.sbtest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import io.github.anantharajuc.sbtest.exception.ResourceNotFoundException;
import io.github.anantharajuc.sbtest.model.Person;
import io.github.anantharajuc.sbtest.repository.PersonRepository;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class PersonServiceImpl implements PersonService
{
	@Autowired
	private PersonRepository personRepository;
	
	@Override
	public List<Person> getAllPersons() 
	{
		log.info("-----> getAllPersons service");	
		
		return personRepository
				.findAll();
	}

	@Override
	public Person getPersonById(Long personId) 
	{
		return personRepository
				.findById(personId)
				.orElseThrow(() -> new ResourceNotFoundException("Person", "id", personId));
	}

	@Override
	public Person createPerson(Person person) 
	{
		return personRepository
				.save(person);
	}

	@Override
	public ResponseEntity<?> deletePerson(Long personId) 
	{
		Person person = personRepository
				.findById(personId)
				.orElseThrow(() -> new ResourceNotFoundException("Person", "id", personId));
		
		personRepository.delete(person);
		
		return ResponseEntity
				.ok()
				.build();
	}

	@Override
	public Person updatePerson(Long personId, Person personDetails) 
	{
		Person person = personRepository
						.findById(personId)
						.orElseThrow(() -> new ResourceNotFoundException("Person", "id", personId));
		
		person.setName(personDetails.getName());
		person.setGender(personDetails.getGender());
		person.setAge(personDetails.getAge());

		return personRepository.save(person);
	}
}
