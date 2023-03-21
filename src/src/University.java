package src;

import java.util.*;

public class University {
	private String state_province;
	private List<String> domains = new ArrayList<>();
	private String country;
	private List<String> web_pages = new ArrayList<>();
	private String name;
	private String alpha_two_code;
	
	public void set_state_province(String state_province) {
        this.state_province = state_province;
    }

    public String get_state_province() {
        return state_province;
    }
    
    public void set_domains(List<String> domains) {
        this.domains = domains;
    }

    public List<String> get_domains() {
        return domains;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }
    
    public void set_web_pages(List<String> web_pages) {
        this.web_pages = web_pages;
    }

    public List<String> get_web_pages() {
        return web_pages;
    }
    
    public void set_name(String name) {
        this.name = name;
    }

    public String get_name() {
        return name;
    }
    
    public void set_alpha_two_code(String alpha_two_code) {
        this.alpha_two_code = alpha_two_code;
    }

    public String get_alpha_two_code() {
        return alpha_two_code;
    }
}
