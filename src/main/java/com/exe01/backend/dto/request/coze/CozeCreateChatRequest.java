package com.exe01.backend.dto.request.coze;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CozeCreateChatRequest {
    @JsonProperty("bot_id")
    private String botId = "7383998577670160392";
    @JsonProperty("user_id")
    private String userId ="demo";
    private boolean stream = false;
    @JsonProperty("auto_save_history")
    private boolean autoSaveHistory=true;
    @JsonProperty("additional_messages")
    private List<AdditionalMessage> additionalMessages;

    @Data
    public static class AdditionalMessage {
        private String role = "user";
        @JsonProperty("content_type")
        private String contentType = "object_string";
        private String content;
        public AdditionalMessage(String fileId) {
            this.content = "[{\"type\":\"text\",\"text\":\"Review CV following this format:\\nHead1 - Spelling\\n   spe1 - \\n   spe2 - \\n   spe3 - \\nHead2 - Sentences to Improve\\n   sent1 - \\n   sent2 - \\nHead3 - Suitable Positions\\n   sp1 - In the \\\"\\\"... .Perhaps,\\\"\\\"\\n   sp2 - \\n   sp2-1 - \\n   sp2-2 - \\n   sp2-3 - \\n\\nFor example:\\nHead1 - Spelling\\nspe1 - \\\"Builted\\\" should be \\\"Built\\\" in the \\\"FPT Software HCM\\\" section.\\nspe2 - \\\"Gitlad\\\" should be \\\"GitLab\\\" in the \\\"Technical Skills\\\" section.\\nspe3 - \\\"apllying\\\" should be \\\"applying\\\" in the \\\"Career Objective\\\" section.\\n\\nHead2 - Sentences to Improve\\nsent1 - In the \\\"FPT Software HCM\\\" section, the sentence \\\"Worked with English-language documentation for technical and project requirements\\\" could be rephrased for better flow. Perhaps, \\\"I utilized English-language documentation for both technical and project requirements.\\\"\\nsent2 - In the \\\"Tortee - Web Application\\\" section, the sentence \\\"Pick up user stories, analyze, design, discuss with the team to tackle them and write documentations to clarify my work\\\" is a bit long and could be broken down into two sentences. Perhaps, \\\"I picked up user stories, analyzed them, and designed solutions. I then collaborated with the team to discuss how to tackle these tasks and wrote clear documentation to clarify my work.\\\"\\n\\nHead3 - Suitable Positions\\nsp1 - Based on your CV, you're showcasing a strong skillset in full-stack development. You've demonstrated experience with various technologies, including ReactJS, Spring Boot, ASP.NET Core, and databases like MySQL and PostgreSQL. You've also participated in multiple projects, showcasing your ability to work in a team environment.\\nsp2 - I would recommend focusing on positions like:\\nsp2-1 - Full-Stack Developer: This is a natural fit given your skills and experience.\\nsp2-2 - Software Engineer: This is a broader term, but your CV demonstrates the technical skills needed for this role.\\nsp2-3 - Web Developer: If you want to highlight your front-end expertise, this is a good option.\"}, {\"type\":\"file\",\"file_id\":\"" + fileId + "\"}]";         }
        public AdditionalMessage(CozeCreateCoverLetterRequest request) {
            this.content = "[{\"type\":\"text\",\"text\":\"hãy viết cho tôi 1 cover bằng tiếng" +request.getLanguage()+ "letter follow this format\\nsdt: "+request.getPhone()+"\\ntên tôi là:+"+request.getName()+"\\nemail: "+request.getEmail()+"\\nvị trí muốn ứng tuyển"+request.getPosition()+ "\\ncông ty muốn ứng tuyển: "+request.getCompany()+"\\n\\nDate and contact information\\n[Your Name]\\n[Your Phone Number]\\n[Your Email]\\n[Date]\\nSalutation/greeting\\nDear [Hiring Manager Name],\\nOpening paragraph\\nI'm excited to be applying for the Web Developer position at [Company Name]. I've been programming websites and using CSS to create user-friendly experiences since I was in middle school, so it's always been a passion of mine. I've also been intrigued by your company since it won Most Innovative at the National Web Development Awards two years ago. I strive to stay on the cutting edge of web design and development, so when I saw this job posting, I knew I had to apply.\\nMiddle paragraph(s)\\nDuring my previous role at [Company Name], I built a website completely from scratch for a recently rebranded business, both ahead of schedule and within budget. I started by gathering requirements from my clients and holding a focus group to perform user research. My favorite part about web design is building a solution that expresses the client and meets the needs of users and customers. My new website was responsive, extremely fast, and included the latest e-commerce features. After launch, I continued to lead optimization efforts. Through A/B testing, I improved the click-through rate by 10% and reduced the bounce rate on the website's landing page by 35%. As your Web Developer, I would bring these skills to develop websites that exceed the expectations of clients and customers, and drive real business results.\\nClosing paragraph\\nOne of the factors that really attracted me to this role is that [Company Name] values giving back to the community. In my spare time, I run free web development workshops for at-risk youth. In these workshops, I teach them the basics of HTML/CSS and JavaScript and serve as a mentor. As I grow in my career, applying my skills to help others and make an impact on the world becomes more important—I believe this would be a great opportunity.\\nThank you for your consideration and time. I'm looking forward to learning more details about the position and company.\\nComplimentary close and signature\\nSincerely,\\n[Your Name]\\n\\n hãy viết bằng ngôn ngữ"+request.getLanguage()+"\"}]";
    }
    }


}
