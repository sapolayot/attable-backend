package com.project.attable.graphql.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.project.attable.entity.Allergic;
import com.project.attable.entity.Banner;
import com.project.attable.repository.AllergicRepository;
import com.project.attable.repository.BannerRepository;

@Component
public class SampleQuery implements GraphQLQueryResolver {
  @Autowired
  AllergicRepository allergicRepository;

  @Autowired
  BannerRepository bannerRepository;

  public List<Allergic> getListAllergics() {
    return allergicRepository.findAll();
  }
  public List<Banner> getListBanner(){
    return bannerRepository.findAll();
  }

}
