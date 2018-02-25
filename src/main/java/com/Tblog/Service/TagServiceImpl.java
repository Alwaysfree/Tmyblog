package com.Tblog.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Tblog.Dao.TagRepository;
import com.Tblog.domain.Tag;
@Service
public class TagServiceImpl implements TagService{
	@Autowired 
	private TagRepository tagRepository;
	
	/**    
	 * 更新tag
	 * @param: tag
	 * return  Tag  
	 */ 
	@Override
	public Tag updataTag(Tag tag) {
		Tag t = tagRepository.findOne(tag.getId());
		t.setTagName(tag.getTagName());
		return tagRepository.saveAndFlush(t);
	}
	
}