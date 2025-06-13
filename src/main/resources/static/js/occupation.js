const occupationSchema = {
    "title": "Occupation Form",
    "type": "object",
    "properties": {
      "occupation": {
        "type": "string",
        "title": "Occupation",
        "description": "Enter your current occupation or job title.",
        "enum": [
          "Student",
          "Software Engineer",
          "Doctor",
          "Teacher",
          "Business Owner",
          "Driver",
          "Freelancer",
          "Unemployed",
          "Other"
        ]
      },
      "organization": {
        "type": "string",
        "title": "Organization/Institute Name",
        "description": "Enter the name of the organization or institute you are associated with.",
        "maxLength": 100
      },
      "workLocation": {
        "type": "string",
        "title": "Work/Study Location",
        "description": "Enter the address or general area of your work or study location.",
        "maxLength": 200
      },
      "workHours": {
        "type": "string",
        "title": "Working Hours",
        "description": "Mention your general working hours (e.g., 9 AM - 6 PM)",
        "maxLength": 50
      }
    },
    "required": ["occupation", "organization", "workLocation"]
};
export default occupationSchema;
  