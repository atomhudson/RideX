const cities = [
	{
		"id": "293",
		"name": "Achalpur",
		"state": "Maharashtra"
	},
	{
		"id": "1094",
		"name": "Achhnera",
		"state": "Uttar Pradesh"
	},
	{
		"id": "1203",
		"name": "Adalaj",
		"state": "Gujarat"
	},
	{
		"id": "278",
		"name": "Adilabad",
		"state": "Telangana"
	},
	{
		"id": "192",
		"name": "Adityapur",
		"state": "Jharkhand"
	},
	{
		"id": "202",
		"name": "Adoni",
		"state": "Andhra Pradesh"
	},
	{
		"id": "929",
		"name": "Adoor",
		"state": "Kerala"
	},
	{
		"id": "1200",
		"name": "Adra",
		"state": "West Bengal"
	},
	{
		"id": "1211",
		"name": "Adyar",
		"state": "Karnataka"
	},
	{
		"id": "977",
		"name": "Afzalpur",
		"state": "Karnataka"
	},
	{
		"id": "97",
		"name": "Agartala",
		"state": "Tripura"
	},
	{
		"id": "23",
		"name": "Agra",
		"state": "Uttar Pradesh"
	},
	{
		"id": "4",
		"name": "Ahmedabad",
		"state": "Gujarat"
	},
	{
		"id": "106",
		"name": "Ahmednagar",
		"state": "Maharashtra"
	},
	{
		"id": "122",
		"name": "Aizawl",
		"state": "Mizoram"
	},
	{
		"id": "74",
		"name": "Ajmer",
		"state": "Rajasthan"
	},
	{
		"id": "92",
		"name": "Akola",
		"state": "Maharashtra"
	},
	{
		"id": "355",
		"name": "Akot",
		"state": "Maharashtra"
	},
	{
		"id": "193",
		"name": "Alappuzha",
		"state": "Kerala"
	},
	{
		"id": "49",
		"name": "Aligarh",
		"state": "Uttar Pradesh"
	},
	{
		"id": "485",
		"name": "AlipurdUrban Agglomerationr",
		"state": "West Bengal"
	},
	{
		"id": "486",
		"name": "Alirajpur",
		"state": "Madhya Pradesh"
	},
	{
		"id": "37",
		"name": "Allahabad",
		"state": "Uttar Pradesh"
	},
	{
		"id": "112",
		"name": "Alwar",
		"state": "Rajasthan"
	},
	{
		"id": "585",
		"name": "Amalapuram",
		"state": "Andhra Pradesh"
	},
	{
		"id": "345",
		"name": "Amalner",
		"state": "Maharashtra"
	},
	{
		"id": "424",
		"name": "Ambejogai",
		"state": "Maharashtra"
	},
	{
		"id": "286",
		"name": "Ambikapur",
		"state": "Chhattisgarh"
	},
	{
		"id": "62",
		"name": "Amravati",
		"state": "Maharashtra"
	},
	{
		"id": "313",
		"name": "Amreli",
		"state": "Gujarat"
	},
	{
		"id": "35",
		"name": "Amritsar",
		"state": "Punjab"
	},
	{
		"id": "46",
		"name": "Amroha",
		"state": "Uttar Pradesh"
	},
	{
		"id": "382",
		"name": "Anakapalle",
		"state": "Andhra Pradesh"
	},
	{
		"id": "173",
		"name": "Anand",
		"state": "Gujarat"
	},
	{
		"id": "136",
		"name": "Anantapur",
		"state": "Andhra Pradesh"
	},
	{
		"id": "305",
		"name": "Anantnag",
		"state": "Jammu and Kashmir"
	},
	{
		"id": "553",
		"name": "Anjangaon",
		"state": "Maharashtra"
	},
	{
		"id": "379",
		"name": "Anjar",
		"state": "Gujarat"
	},
	{
		"id": "425",
		"name": "Ankleshwar",
		"state": "Gujarat"
	},
	{
		"id": "409",
		"name": "Arakkonam",
		"state": "Tamil Nadu"
	},
	{
		"id": "477",
		"name": "Arambagh",
		"state": "West Bengal"
	},
	{
		"id": "405",
		"name": "Araria",
		"state": "Bihar"
	},
	{
		"id": "137",
		"name": "Arrah",
		"state": "Bihar"
	},
	{
		"id": "603",
		"name": "Arsikere",
		"state": "Karnataka"
	},
	{
		"id": "374",
		"name": "Aruppukkottai",
		"state": "Tamil Nadu"
	},
	{
		"id": "698",
		"name": "Arvi",
		"state": "Maharashtra"
	},
	{
		"id": "598",
		"name": "Arwal",
		"state": "Bihar"
	},
	{
		"id": "72",
		"name": "Asansol",
		"state": "West Bengal"
	},
	{
		"id": "1214",
		"name": "Asarganj",
		"state": "Bihar"
	},
	{
		"id": "395",
		"name": "Ashok Nagar",
		"state": "Madhya Pradesh"
	},
	{
		"id": "665",
		"name": "Athni",
		"state": "Karnataka"
	},
	{
		"id": "767",
		"name": "Attingal",
		"state": "Kerala"
	},
	{
		"id": "321",
		"name": "Aurangabad",
		"state": "Maharashtra"
	},
	{
		"id": "328",
		"name": "Aurangabad",
		"state": "Bihar"
	},
	{
		"id": "297",
		"name": "Azamgarh",
		"state": "Uttar Pradesh"
	},
	{
		"id": "291",
		"name": "Bagaha",
		"state": "Bihar"
	},
	{
		"id": "1209",
		"name": "Bageshwar",
		"state": "Uttarakhand"
	},
	{
		"id": "194",
		"name": "Bahadurgarh",
		"state": "Haryana"
	},
	{
		"id": "175",
		"name": "Baharampur",
		"state": "West Bengal"
	},
	{
		"id": "179",
		"name": "Bahraich",
		"state": "Uttar Pradesh"
	},
	{
		"id": "388",
		"name": "Balaghat",
		"state": "Madhya Pradesh"
	},
	{
		"id": "340",
		"name": "Balangir",
		"state": "Odisha"
	},
	{
		"id": "275",
		"name": "Baleshwar Town",
		"state": "Odisha"
	},
	{
		"id": "96",
		"name": "Ballari",
		"state": "Karnataka"
	},
	{
		"id": "225",
		"name": "Balurghat",
		"state": "West Bengal"
	},
	{
		"id": "247",
		"name": "Bankura",
		"state": "West Bengal"
	},
	{
		"id": "446",
		"name": "Bapatla",
		"state": "Andhra Pradesh"
	},
	{
		"id": "201",
		"name": "Baramula",
		"state": "Jammu and Kashmir"
	},
	{
		"id": "474",
		"name": "Barbil",
		"state": "Odisha"
	},
	{
		"id": "398",
		"name": "Bargarh",
		"state": "Odisha"
	},
	{
		"id": "1205",
		"name": "Barh",
		"state": "Bihar"
	},
	{
		"id": "300",
		"name": "Baripada Town",
		"state": "Odisha"
	},
	{
		"id": "176",
		"name": "Barmer",
		"state": "Rajasthan"
	},
	{
		"id": "280",
		"name": "Barnala",
		"state": "Punjab"
	},
	{
		"id": "700",
		"name": "Barpeta",
		"state": "Assam"
	},
	{
		"id": "214",
		"name": "Batala",
		"state": "Punjab"
	},
	{
		"id": "126",
		"name": "Bathinda",
		"state": "Punjab"
	},
	{
		"id": "142",
		"name": "Begusarai",
		"state": "Bihar"
	},
	{
		"id": "83",
		"name": "Belagavi",
		"state": "Karnataka"
	},
	{
		"id": "574",
		"name": "Bellampalle",
		"state": "Telangana"
	},
	{
		"id": "1191",
		"name": "Belonia",
		"state": "Tripura"
	},
	{
		"id": "3",
		"name": "Bengaluru",
		"state": "Karnataka"
	},
	{
		"id": "253",
		"name": "Bettiah",
		"state": "Bihar"
	},
	{
		"id": "613",
		"name": "BhabUrban Agglomeration",
		"state": "Bihar"
	},
	{
		"id": "617",
		"name": "Bhadrachalam",
		"state": "Telangana"
	},
	{
		"id": "308",
		"name": "Bhadrak",
		"state": "Odisha"
	},
	{
		"id": "98",
		"name": "Bhagalpur",
		"state": "Bihar"
	},
	{
		"id": "624",
		"name": "Bhainsa",
		"state": "Telangana"
	},
	{
		"id": "141",
		"name": "Bharatpur",
		"state": "Rajasthan"
	},
	{
		"id": "198",
		"name": "Bharuch",
		"state": "Gujarat"
	},
	{
		"id": "543",
		"name": "Bhatapara",
		"state": "Chhattisgarh"
	},
	{
		"id": "70",
		"name": "Bhavnagar",
		"state": "Gujarat"
	},
	{
		"id": "457",
		"name": "Bhawanipatna",
		"state": "Odisha"
	},
	{
		"id": "593",
		"name": "Bheemunipatnam",
		"state": "Andhra Pradesh"
	},
	{
		"id": "66",
		"name": "Bhilai Nagar",
		"state": "Chhattisgarh"
	},
	{
		"id": "102",
		"name": "Bhilwara",
		"state": "Rajasthan"
	},
	{
		"id": "243",
		"name": "Bhimavaram",
		"state": "Andhra Pradesh"
	},
	{
		"id": "59",
		"name": "Bhiwandi",
		"state": "Maharashtra"
	},
	{
		"id": "170",
		"name": "Bhiwani",
		"state": "Haryana"
	},
	{
		"id": "584",
		"name": "Bhongir",
		"state": "Telangana"
	},
	{
		"id": "17",
		"name": "Bhopal",
		"state": "Madhya Pradesh"
	},
	{
		"id": "54",
		"name": "Bhubaneswar",
		"state": "Odisha"
	},
	{
		"id": "234",
		"name": "Bhuj",
		"state": "Gujarat"
	},
	{
		"id": "63",
		"name": "Bikaner",
		"state": "Rajasthan"
	},
	{
		"id": "109",
		"name": "Bilaspur",
		"state": "Chhattisgarh"
	},
	{
		"id": "550",
		"name": "Bobbili",
		"state": "Andhra Pradesh"
	},
	{
		"id": "412",
		"name": "Bodhan",
		"state": "Telangana"
	},
	{
		"id": "94",
		"name": "Bokaro Steel City",
		"state": "Jharkhand"
	},
	{
		"id": "465",
		"name": "Bongaigaon City",
		"state": "Assam"
	},
	{
		"id": "103",
		"name": "Brahmapur",
		"state": "Odisha"
	},
	{
		"id": "320",
		"name": "Buxar",
		"state": "Bihar"
	},
	{
		"id": "635",
		"name": "Byasanagar",
		"state": "Odisha"
	},
	{
		"id": "454",
		"name": "Chaibasa",
		"state": "Jharkhand"
	},
	{
		"id": "627",
		"name": "Chalakudy",
		"state": "Kerala"
	},
	{
		"id": "288",
		"name": "Chandausi",
		"state": "Uttar Pradesh"
	},
	{
		"id": "44",
		"name": "Chandigarh",
		"state": "Chandigarh"
	},
	{
		"id": "646",
		"name": "Changanassery",
		"state": "Kerala"
	},
	{
		"id": "618",
		"name": "Charkhi Dadri",
		"state": "Haryana"
	},
	{
		"id": "619",
		"name": "Chatra",
		"state": "Jharkhand"
	},
	{
		"id": "6",
		"name": "Chennai",
		"state": "Tamil Nadu"
	},
	{
		"id": "666",
		"name": "Cherthala",
		"state": "Kerala"
	},
	{
		"id": "144",
		"name": "Chhapra",
		"state": "Bihar"
	},
	{
		"id": "1206",
		"name": "Chhapra",
		"state": "Gujarat"
	},
	{
		"id": "273",
		"name": "Chikkamagaluru",
		"state": "Karnataka"
	},
	{
		"id": "327",
		"name": "Chilakaluripet",
		"state": "Andhra Pradesh"
	},
	{
		"id": "378",
		"name": "Chirala",
		"state": "Andhra Pradesh"
	},
	{
		"id": "670",
		"name": "Chirkunda",
		"state": "Jharkhand"
	},
	{
		"id": "455",
		"name": "Chirmiri",
		"state": "Chhattisgarh"
	},
	{
		"id": "219",
		"name": "Chittoor",
		"state": "Andhra Pradesh"
	},
	{
		"id": "867",
		"name": "Chittur-Thathamangalam",
		"state": "Kerala"
	},
	{
		"id": "38",
		"name": "Coimbatore",
		"state": "Tamil Nadu"
	},
	{
		"id": "67",
		"name": "Cuttack",
		"state": "Odisha"
	},
	{
		"id": "681",
		"name": "Dalli-Rajhara",
		"state": "Chhattisgarh"
	},
	{
		"id": "120",
		"name": "Darbhanga",
		"state": "Bihar"
	},
	{
		"id": "272",
		"name": "Darjiling",
		"state": "West Bengal"
	},
	{
		"id": "90",
		"name": "Davanagere",
		"state": "Karnataka"
	},
	{
		"id": "295",
		"name": "Deesa",
		"state": "Gujarat"
	},
	{
		"id": "71",
		"name": "Dehradun",
		"state": "Uttarakhand"
	},
	{
		"id": "249",
		"name": "Dehri-on-Sone",
		"state": "Bihar"
	},
	{
		"id": "2",
		"name": "Delhi",
		"state": "Delhi"
	},
	{
		"id": "166",
		"name": "Deoghar",
		"state": "Jharkhand"
	},
	{
		"id": "394",
		"name": "Dhamtari",
		"state": "Chhattisgarh"
	},
	{
		"id": "33",
		"name": "Dhanbad",
		"state": "Jharkhand"
	},
	{
		"id": "898",
		"name": "Dharmanagar",
		"state": "Tripura"
	},
	{
		"id": "269",
		"name": "Dharmavaram",
		"state": "Andhra Pradesh"
	},
	{
		"id": "462",
		"name": "Dhenkanal",
		"state": "Odisha"
	},
	{
		"id": "386",
		"name": "Dhoraji",
		"state": "Gujarat"
	},
	{
		"id": "492",
		"name": "Dhubri",
		"state": "Assam"
	},
	{
		"id": "100",
		"name": "Dhule",
		"state": "Maharashtra"
	},
	{
		"id": "566",
		"name": "Dhuri",
		"state": "Punjab"
	},
	{
		"id": "245",
		"name": "Dibrugarh",
		"state": "Assam"
	},
	{
		"id": "265",
		"name": "Dimapur",
		"state": "Nagaland"
	},
	{
		"id": "507",
		"name": "Diphu",
		"state": "Assam"
	},
	{
		"id": "1221",
		"name": "Dispur",
		"state": "Assam"
	},
	{
		"id": "648",
		"name": "Dumka",
		"state": "Jharkhand"
	},
	{
		"id": "578",
		"name": "Dumraon",
		"state": "Bihar"
	},
	{
		"id": "131",
		"name": "Durg",
		"state": "Chhattisgarh"
	},
	{
		"id": "161",
		"name": "Eluru",
		"state": "Andhra Pradesh"
	},
	{
		"id": "160",
		"name": "English Bazar",
		"state": "West Bengal"
	},
	{
		"id": "213",
		"name": "Erode",
		"state": "Tamil Nadu"
	},
	{
		"id": "140",
		"name": "Etawah",
		"state": "Uttar Pradesh"
	},
	{
		"id": "26",
		"name": "Faridabad",
		"state": "Haryana"
	},
	{
		"id": "384",
		"name": "Faridkot",
		"state": "Punjab"
	},
	{
		"id": "668",
		"name": "Farooqnagar",
		"state": "Telangana"
	},
	{
		"id": "447",
		"name": "Fatehabad",
		"state": "Haryana"
	},
	{
		"id": "848",
		"name": "Fatehpur Sikri",
		"state": "Uttar Pradesh"
	},
	{
		"id": "413",
		"name": "Fazilka",
		"state": "Punjab"
	},
	{
		"id": "20",
		"name": "Firozabad",
		"state": "Uttar Pradesh"
	},
	{
		"id": "299",
		"name": "Firozpur",
		"state": "Punjab"
	},
	{
		"id": "586",
		"name": "Firozpur Cantt.",
		"state": "Punjab"
	},
	{
		"id": "611",
		"name": "Forbesganj",
		"state": "Bihar"
	},
	{
		"id": "542",
		"name": "Gadwal",
		"state": "Telangana"
	},
	{
		"id": "1216",
		"name": "Gandhinagar",
		"state": "Gujarat"
	},
	{
		"id": "556",
		"name": "Gangarampur",
		"state": "West Bengal"
	},
	{
		"id": "226",
		"name": "Ganjbasoda",
		"state": "Madhya Pradesh"
	},
	{
		"id": "88",
		"name": "Gaya",
		"state": "Bihar"
	},
	{
		"id": "287",
		"name": "Giridih",
		"state": "Jharkhand"
	},
	{
		"id": "581",
		"name": "Goalpara",
		"state": "Assam"
	},
	{
		"id": "523",
		"name": "Gobichettipalayam",
		"state": "Tamil Nadu"
	},
	{
		"id": "432",
		"name": "Gobindgarh",
		"state": "Punjab"
	},
	{
		"id": "241",
		"name": "Godhra",
		"state": "Gujarat"
	},
	{
		"id": "478",
		"name": "Gohana",
		"state": "Haryana"
	},
	{
		"id": "403",
		"name": "Gokak",
		"state": "Karnataka"
	},
	{
		"id": "637",
		"name": "Gooty",
		"state": "Andhra Pradesh"
	},
	{
		"id": "464",
		"name": "Gopalganj",
		"state": "Bihar"
	},
	{
		"id": "274",
		"name": "Gudivada",
		"state": "Andhra Pradesh"
	},
	{
		"id": "515",
		"name": "Gudur",
		"state": "Andhra Pradesh"
	},
	{
		"id": "642",
		"name": "Gumia",
		"state": "Jharkhand"
	},
	{
		"id": "259",
		"name": "Guntakal",
		"state": "Andhra Pradesh"
	},
	{
		"id": "61",
		"name": "Guntur",
		"state": "Andhra Pradesh"
	},
	{
		"id": "418",
		"name": "Gurdaspur",
		"state": "Punjab"
	},
	{
		"id": "48",
		"name": "Gurgaon",
		"state": "Haryana"
	},
	{
		"id": "1176",
		"name": "Guruvayoor",
		"state": "Kerala"
	},
	{
		"id": "43",
		"name": "Guwahati",
		"state": "Assam"
	},
	{
		"id": "40",
		"name": "Gwalior",
		"state": "Madhya Pradesh"
	},
	{
		"id": "231",
		"name": "Habra",
		"state": "West Bengal"
	},
	{
		"id": "233",
		"name": "Hajipur",
		"state": "Bihar"
	},
	{
		"id": "215",
		"name": "Haldwani-cum-Kathgodam",
		"state": "Uttarakhand"
	},
	{
		"id": "381",
		"name": "Hansi",
		"state": "Haryana"
	},
	{
		"id": "134",
		"name": "Hapur",
		"state": "Uttar Pradesh"
	},
	{
		"id": "292",
		"name": "Hardoi ",
		"state": "Uttar Pradesh"
	},
	{
		"id": "151",
		"name": "Hardwar",
		"state": "Uttarakhand"
	},
	{
		"id": "242",
		"name": "Hazaribag",
		"state": "Jharkhand"
	},
	{
		"id": "223",
		"name": "Hindupur",
		"state": "Andhra Pradesh"
	},
	{
		"id": "117",
		"name": "Hisar",
		"state": "Haryana"
	},
	{
		"id": "200",
		"name": "Hoshiarpur",
		"state": "Punjab"
	},
	{
		"id": "45",
		"name": "Hubli-Dharwad",
		"state": "Karnataka"
	},
	{
		"id": "189",
		"name": "Hugli-Chinsurah",
		"state": "West Bengal"
	},
	{
		"id": "5",
		"name": "Hyderabad",
		"state": "Telangana"
	},
	{
		"id": "123",
		"name": "Ichalkaranji",
		"state": "Maharashtra"
	},
	{
		"id": "133",
		"name": "Imphal",
		"state": "Manipur"
	},
	{
		"id": "15",
		"name": "Indore",
		"state": "Madhya Pradesh"
	},
	{
		"id": "338",
		"name": "Itarsi",
		"state": "Madhya Pradesh"
	},
	{
		"id": "39",
		"name": "Jabalpur",
		"state": "Madhya Pradesh"
	},
	{
		"id": "262",
		"name": "Jagdalpur",
		"state": "Chhattisgarh"
	},
	{
		"id": "580",
		"name": "Jaggaiahpet",
		"state": "Andhra Pradesh"
	},
	{
		"id": "484",
		"name": "Jagraon",
		"state": "Punjab"
	},
	{
		"id": "343",
		"name": "Jagtial",
		"state": "Telangana"
	},
	{
		"id": "9",
		"name": "Jaipur",
		"state": "Rajasthan"
	},
	{
		"id": "52",
		"name": "Jalandhar",
		"state": "Punjab"
	},
	{
		"id": "644",
		"name": "Jalandhar Cantt.",
		"state": "Punjab"
	},
	{
		"id": "197",
		"name": "Jalpaiguri",
		"state": "West Bengal"
	},
	{
		"id": "315",
		"name": "Jamalpur",
		"state": "Bihar"
	},
	{
		"id": "662",
		"name": "Jammalamadugu",
		"state": "Andhra Pradesh"
	},
	{
		"id": "82",
		"name": "Jammu",
		"state": "Jammu and Kashmir"
	},
	{
		"id": "75",
		"name": "Jamnagar",
		"state": "Gujarat"
	},
	{
		"id": "65",
		"name": "Jamshedpur",
		"state": "Jharkhand"
	},
	{
		"id": "376",
		"name": "Jamui",
		"state": "Bihar"
	},
	{
		"id": "592",
		"name": "Jangaon",
		"state": "Telangana"
	},
	{
		"id": "560",
		"name": "Jatani",
		"state": "Odisha"
	},
	{
		"id": "322",
		"name": "Jehanabad",
		"state": "Bihar"
	},
	{
		"id": "79",
		"name": "Jhansi",
		"state": "Uttar Pradesh"
	},
	{
		"id": "508",
		"name": "Jhargram",
		"state": "West Bengal"
	},
	{
		"id": "342",
		"name": "Jharsuguda",
		"state": "Odisha"
	},
	{
		"id": "373",
		"name": "Jhumri Tilaiya",
		"state": "Jharkhand"
	},
	{
		"id": "203",
		"name": "Jind",
		"state": "Haryana"
	},
	{
		"id": "34",
		"name": "Jodhpur",
		"state": "Rajasthan"
	},
	{
		"id": "441",
		"name": "Jorhat",
		"state": "Assam"
	},
	{
		"id": "145",
		"name": "Kadapa",
		"state": "Andhra Pradesh"
	},
	{
		"id": "430",
		"name": "Kadi",
		"state": "Gujarat"
	},
	{
		"id": "365",
		"name": "Kadiri",
		"state": "Andhra Pradesh"
	},
	{
		"id": "541",
		"name": "Kagaznagar",
		"state": "Telangana"
	},
	{
		"id": "1182",
		"name": "Kailasahar",
		"state": "Tripura"
	},
	{
		"id": "239",
		"name": "Kaithal",
		"state": "Haryana"
	},
	{
		"id": "113",
		"name": "Kakinada",
		"state": "Andhra Pradesh"
	},
	{
		"id": "696",
		"name": "Kalimpong",
		"state": "West Bengal"
	},
	{
		"id": "600",
		"name": "Kalpi",
		"state": "Uttar Pradesh"
	},
	{
		"id": "29",
		"name": "Kalyan-Dombivali",
		"state": "Maharashtra"
	},
	{
		"id": "399",
		"name": "Kamareddy",
		"state": "Telangana"
	},
	{
		"id": "206",
		"name": "Kancheepuram",
		"state": "Tamil Nadu"
	},
	{
		"id": "544",
		"name": "Kandukur",
		"state": "Andhra Pradesh"
	},
	{
		"id": "429",
		"name": "Kanhangad",
		"state": "Kerala"
	},
	{
		"id": "549",
		"name": "Kannur",
		"state": "Kerala"
	},
	{
		"id": "12",
		"name": "Kanpur",
		"state": "Uttar Pradesh"
	},
	{
		"id": "629",
		"name": "Kapadvanj",
		"state": "Gujarat"
	},
	{
		"id": "326",
		"name": "Kapurthala",
		"state": "Punjab"
	},
	{
		"id": "380",
		"name": "Karaikal",
		"state": "Puducherry"
	},
	{
		"id": "548",
		"name": "Karimganj",
		"state": "Assam"
	},
	{
		"id": "138",
		"name": "Karimnagar",
		"state": "Telangana"
	},
	{
		"id": "914",
		"name": "Karjat",
		"state": "Maharashtra"
	},
	{
		"id": "125",
		"name": "Karnal",
		"state": "Haryana"
	},
	{
		"id": "442",
		"name": "Karur",
		"state": "Tamil Nadu"
	},
	{
		"id": "301",
		"name": "Karwar",
		"state": "Karnataka"
	},
	{
		"id": "568",
		"name": "Kasaragod",
		"state": "Kerala"
	},
	{
		"id": "270",
		"name": "Kashipur",
		"state": "Uttarakhand"
	},
	{
		"id": "596",
		"name": "KathUrban Agglomeration",
		"state": "Jammu and Kashmir"
	},
	{
		"id": "150",
		"name": "Katihar",
		"state": "Bihar"
	},
	{
		"id": "393",
		"name": "Kavali",
		"state": "Andhra Pradesh"
	},
	{
		"id": "458",
		"name": "Kayamkulam",
		"state": "Kerala"
	},
	{
		"id": "654",
		"name": "Kendrapara",
		"state": "Odisha"
	},
	{
		"id": "516",
		"name": "Kendujhar",
		"state": "Odisha"
	},
	{
		"id": "415",
		"name": "Keshod",
		"state": "Gujarat"
	},
	{
		"id": "324",
		"name": "Khair",
		"state": "Uttar Pradesh"
	},
	{
		"id": "390",
		"name": "Khambhat",
		"state": "Gujarat"
	},
	{
		"id": "183",
		"name": "Khammam",
		"state": "Telangana"
	},
	{
		"id": "255",
		"name": "Khanna",
		"state": "Punjab"
	},
	{
		"id": "121",
		"name": "Kharagpur",
		"state": "West Bengal"
	},
	{
		"id": "422",
		"name": "Kharar",
		"state": "Punjab"
	},
	{
		"id": "1196",
		"name": "Khowai",
		"state": "Tripura"
	},
	{
		"id": "309",
		"name": "Kishanganj",
		"state": "Bihar"
	},
	{
		"id": "68",
		"name": "Kochi",
		"state": "Kerala"
	},
	{
		"id": "827",
		"name": "Kodungallur",
		"state": "Kerala"
	},
	{
		"id": "339",
		"name": "Kohima",
		"state": "Nagaland"
	},
	{
		"id": "246",
		"name": "Kolar",
		"state": "Karnataka"
	},
	{
		"id": "7",
		"name": "Kolkata",
		"state": "West Bengal"
	},
	{
		"id": "107",
		"name": "Kollam",
		"state": "Kerala"
	},
	{
		"id": "475",
		"name": "Koratla",
		"state": "Telangana"
	},
	{
		"id": "101",
		"name": "Korba",
		"state": "Chhattisgarh"
	},
	{
		"id": "358",
		"name": "Kot Kapura",
		"state": "Punjab"
	},
	{
		"id": "1220",
		"name": "Kota",
		"state": "Rajasthan"
	},
	{
		"id": "401",
		"name": "Kothagudem",
		"state": "Telangana"
	},
	{
		"id": "564",
		"name": "Kottayam",
		"state": "Kerala"
	},
	{
		"id": "738",
		"name": "Kovvur",
		"state": "Andhra Pradesh"
	},
	{
		"id": "440",
		"name": "Koyilandy",
		"state": "Kerala"
	},
	{
		"id": "91",
		"name": "Kozhikode",
		"state": "Kerala"
	},
	{
		"id": "1217",
		"name": "Kullu",
		"state": "Himachal Pradesh"
	},
	{
		"id": "572",
		"name": "Kunnamkulam",
		"state": "Kerala"
	},
	{
		"id": "93",
		"name": "Kurnool",
		"state": "Andhra Pradesh"
	},
	{
		"id": "863",
		"name": "Kyathampalle",
		"state": "Telangana"
	},
	{
		"id": "583",
		"name": "Lachhmangarh",
		"state": "Rajasthan"
	},
	{
		"id": "479",
		"name": "Ladnu",
		"state": "Rajasthan"
	},
	{
		"id": "933",
		"name": "Ladwa",
		"state": "Haryana"
	},
	{
		"id": "795",
		"name": "Lahar",
		"state": "Madhya Pradesh"
	},
	{
		"id": "502",
		"name": "Laharpur",
		"state": "Uttar Pradesh"
	},
	{
		"id": "918",
		"name": "Lakheri",
		"state": "Rajasthan"
	},
	{
		"id": "221",
		"name": "Lakhimpur",
		"state": "Uttar Pradesh"
	},
	{
		"id": "335",
		"name": "Lakhisarai",
		"state": "Bihar"
	},
	{
		"id": "778",
		"name": "Lakshmeshwar",
		"state": "Karnataka"
	},
	{
		"id": "943",
		"name": "Lal Gopalganj Nindaura",
		"state": "Uttar Pradesh"
	},
	{
		"id": "1081",
		"name": "Lalganj",
		"state": "Bihar"
	},
	{
		"id": "1152",
		"name": "Lalganj",
		"state": "Uttar Pradesh"
	},
	{
		"id": "1058",
		"name": "Lalgudi",
		"state": "Tamil Nadu"
	},
	{
		"id": "252",
		"name": "Lalitpur",
		"state": "Uttar Pradesh"
	},
	{
		"id": "814",
		"name": "Lalsot",
		"state": "Rajasthan"
	},
	{
		"id": "775",
		"name": "Lanka",
		"state": "Assam"
	},
	{
		"id": "942",
		"name": "Lar",
		"state": "Uttar Pradesh"
	},
	{
		"id": "1151",
		"name": "Lathi",
		"state": "Gujarat"
	},
	{
		"id": "99",
		"name": "Latur",
		"state": "Maharashtra"
	},
	{
		"id": "1091",
		"name": "Lilong",
		"state": "Manipur"
	},
	{
		"id": "699",
		"name": "Limbdi",
		"state": "Gujarat"
	},
	{
		"id": "802",
		"name": "Lingsugur",
		"state": "Karnataka"
	},
	{
		"id": "1188",
		"name": "Loha",
		"state": "Maharashtra"
	},
	{
		"id": "1049",
		"name": "Lohardaga",
		"state": "Jharkhand"
	},
	{
		"id": "1070",
		"name": "Lonar",
		"state": "Maharashtra"
	},
	{
		"id": "539",
		"name": "Lonavla",
		"state": "Maharashtra"
	},
	{
		"id": "1056",
		"name": "Longowal",
		"state": "Punjab"
	},
	{
		"id": "78",
		"name": "Loni",
		"state": "Uttar Pradesh"
	},
	{
		"id": "936",
		"name": "Losal",
		"state": "Rajasthan"
	},
	{
		"id": "11",
		"name": "Lucknow",
		"state": "Uttar Pradesh"
	},
	{
		"id": "21",
		"name": "Ludhiana",
		"state": "Punjab"
	},
	{
		"id": "888",
		"name": "Lumding",
		"state": "Assam"
	},
	{
		"id": "772",
		"name": "Lunawada",
		"state": "Gujarat"
	},
	{
		"id": "547",
		"name": "Lunglei",
		"state": "Mizoram"
	},
	{
		"id": "630",
		"name": "Macherla",
		"state": "Andhra Pradesh"
	},
	{
		"id": "195",
		"name": "Machilipatnam",
		"state": "Andhra Pradesh"
	},
	{
		"id": "250",
		"name": "Madanapalle",
		"state": "Andhra Pradesh"
	},
	{
		"id": "991",
		"name": "Maddur",
		"state": "Karnataka"
	},
	{
		"id": "672",
		"name": "Madhepura",
		"state": "Bihar"
	},
	{
		"id": "476",
		"name": "Madhubani",
		"state": "Bihar"
	},
	{
		"id": "995",
		"name": "Madhugiri",
		"state": "Karnataka"
	},
	{
		"id": "651",
		"name": "Madhupur",
		"state": "Jharkhand"
	},
	{
		"id": "859",
		"name": "Madikeri",
		"state": "Karnataka"
	},
	{
		"id": "42",
		"name": "Madurai",
		"state": "Tamil Nadu"
	},
	{
		"id": "1028",
		"name": "Magadi",
		"state": "Karnataka"
	},
	{
		"id": "1048",
		"name": "Mahad",
		"state": "Maharashtra"
	},
	{
		"id": "897",
		"name": "Mahalingapura",
		"state": "Karnataka"
	},
	{
		"id": "1165",
		"name": "Maharajganj",
		"state": "Bihar"
	},
	{
		"id": "1143",
		"name": "Maharajpur",
		"state": "Madhya Pradesh"
	},
	{
		"id": "383",
		"name": "Mahasamund",
		"state": "Chhattisgarh"
	},
	{
		"id": "210",
		"name": "Mahbubnagar",
		"state": "Telangana"
	},
	{
		"id": "774",
		"name": "Mahe",
		"state": "Puducherry"
	},
	{
		"id": "899",
		"name": "Mahemdabad",
		"state": "Gujarat"
	},
	{
		"id": "1045",
		"name": "Mahendragarh",
		"state": "Haryana"
	},
	{
		"id": "184",
		"name": "Mahesana",
		"state": "Gujarat"
	},
	{
		"id": "923",
		"name": "Mahidpur",
		"state": "Madhya Pradesh"
	},
	{
		"id": "766",
		"name": "Mahnar Bazar",
		"state": "Bihar"
	},
	{
		"id": "397",
		"name": "Mahuva",
		"state": "Gujarat"
	},
	{
		"id": "815",
		"name": "Maihar",
		"state": "Madhya Pradesh"
	},
	{
		"id": "976",
		"name": "Mainaguri",
		"state": "West Bengal"
	},
	{
		"id": "905",
		"name": "Makhdumpur",
		"state": "Bihar"
	},
	{
		"id": "359",
		"name": "Makrana",
		"state": "Rajasthan"
	},
	{
		"id": "868",
		"name": "Malaj Khand",
		"state": "Madhya Pradesh"
	},
	{
		"id": "329",
		"name": "Malappuram",
		"state": "Kerala"
	},
	{
		"id": "793",
		"name": "Malavalli",
		"state": "Karnataka"
	},
	{
		"id": "1078",
		"name": "Malda",
		"state": "West Bengal"
	},
	{
		"id": "87",
		"name": "Malegaon",
		"state": "Maharashtra"
	},
	{
		"id": "251",
		"name": "Malerkotla",
		"state": "Punjab"
	},
	{
		"id": "1082",
		"name": "Malkangiri",
		"state": "Odisha"
	},
	{
		"id": "513",
		"name": "Malkapur",
		"state": "Maharashtra"
	},
	{
		"id": "448",
		"name": "Malout",
		"state": "Punjab"
	},
	{
		"id": "971",
		"name": "Malpura",
		"state": "Rajasthan"
	},
	{
		"id": "956",
		"name": "Malur",
		"state": "Karnataka"
	},
	{
		"id": "1005",
		"name": "Manachanallur",
		"state": "Tamil Nadu"
	},
	{
		"id": "1218",
		"name": "Manali",
		"state": "Himachal Praddesh"
	},
	{
		"id": "1100",
		"name": "Manasa",
		"state": "Madhya Pradesh"
	},
	{
		"id": "965",
		"name": "Manavadar",
		"state": "Gujarat"
	},
	{
		"id": "1017",
		"name": "Manawar",
		"state": "Madhya Pradesh"
	},
	{
		"id": "276",
		"name": "Mancherial",
		"state": "Telangana"
	},
	{
		"id": "1187",
		"name": "Mandalgarh",
		"state": "Rajasthan"
	},
	{
		"id": "472",
		"name": "Mandamarri",
		"state": "Telangana"
	},
	{
		"id": "647",
		"name": "Mandapeta",
		"state": "Andhra Pradesh"
	},
	{
		"id": "1168",
		"name": "Mandawa",
		"state": "Rajasthan"
	},
	{
		"id": "518",
		"name": "Mandi",
		"state": "Himachal Pradesh"
	},
	{
		"id": "576",
		"name": "Mandi Dabwali",
		"state": "Haryana"
	},
	{
		"id": "735",
		"name": "Mandideep",
		"state": "Madhya Pradesh"
	},
	{
		"id": "517",
		"name": "Mandla",
		"state": "Madhya Pradesh"
	},
	{
		"id": "244",
		"name": "Mandsaur",
		"state": "Madhya Pradesh"
	},
	{
		"id": "705",
		"name": "Mandvi",
		"state": "Gujarat"
	},
	{
		"id": "248",
		"name": "Mandya",
		"state": "Karnataka"
	},
	{
		"id": "900",
		"name": "Manendragarh",
		"state": "Chhattisgarh"
	},
	{
		"id": "906",
		"name": "Maner",
		"state": "Bihar"
	},
	{
		"id": "1054",
		"name": "Mangaldoi",
		"state": "Assam"
	},
	{
		"id": "85",
		"name": "Mangaluru",
		"state": "Karnataka"
	},
	{
		"id": "1136",
		"name": "Mangalvedhe",
		"state": "Maharashtra"
	},
	{
		"id": "701",
		"name": "Manglaur",
		"state": "Uttarakhand"
	},
	{
		"id": "554",
		"name": "Mangrol",
		"state": "Gujarat"
	},
	{
		"id": "1131",
		"name": "Mangrol",
		"state": "Rajasthan"
	},
	{
		"id": "957",
		"name": "Mangrulpir",
		"state": "Maharashtra"
	},
	{
		"id": "1134",
		"name": "Manihari",
		"state": "Bihar"
	},
	{
		"id": "688",
		"name": "Manjlegaon",
		"state": "Maharashtra"
	},
	{
		"id": "934",
		"name": "Mankachar",
		"state": "Assam"
	},
	{
		"id": "436",
		"name": "Manmad",
		"state": "Maharashtra"
	},
	{
		"id": "434",
		"name": "Mansa",
		"state": "Punjab"
	},
	{
		"id": "951",
		"name": "Mansa",
		"state": "Gujarat"
	},
	{
		"id": "849",
		"name": "Manuguru",
		"state": "Telangana"
	},
	{
		"id": "764",
		"name": "Manvi",
		"state": "Karnataka"
	},
	{
		"id": "928",
		"name": "Manwath",
		"state": "Maharashtra"
	},
	{
		"id": "731",
		"name": "Mapusa",
		"state": "Goa"
	},
	{
		"id": "354",
		"name": "Margao",
		"state": "Goa"
	},
	{
		"id": "1051",
		"name": "Margherita",
		"state": "Assam"
	},
	{
		"id": "1039",
		"name": "Marhaura",
		"state": "Bihar"
	},
	{
		"id": "1157",
		"name": "Mariani",
		"state": "Assam"
	},
	{
		"id": "1169",
		"name": "Marigaon",
		"state": "Assam"
	},
	{
		"id": "531",
		"name": "Markapur",
		"state": "Andhra Pradesh"
	},
	{
		"id": "316",
		"name": "Marmagao",
		"state": "Goa"
	},
	{
		"id": "493",
		"name": "Masaurhi",
		"state": "Bihar"
	},
	{
		"id": "1154",
		"name": "Mathabhanga",
		"state": "West Bengal"
	},
	{
		"id": "684",
		"name": "Mathura",
		"state": "Uttar Pradesh"
	},
	{
		"id": "683",
		"name": "Mattannur",
		"state": "Kerala"
	},
	{
		"id": "1084",
		"name": "Mauganj",
		"state": "Madhya Pradesh"
	},
	{
		"id": "939",
		"name": "Mavelikkara",
		"state": "Kerala"
	},
	{
		"id": "953",
		"name": "Mavoor",
		"state": "Kerala"
	},
	{
		"id": "1174",
		"name": "Mayang Imphal",
		"state": "Manipur"
	},
	{
		"id": "713",
		"name": "Medak",
		"state": "Telangana"
	},
	{
		"id": "408",
		"name": "Medininagar (Daltonganj)",
		"state": "Jharkhand"
	},
	{
		"id": "230",
		"name": "Medinipur",
		"state": "West Bengal"
	},
	{
		"id": "28",
		"name": "Meerut",
		"state": "Uttar Pradesh"
	},
	{
		"id": "761",
		"name": "Mehkar",
		"state": "Maharashtra"
	},
	{
		"id": "787",
		"name": "Memari",
		"state": "West Bengal"
	},
	{
		"id": "733",
		"name": "Merta City",
		"state": "Rajasthan"
	},
	{
		"id": "1177",
		"name": "Mhaswad",
		"state": "Maharashtra"
	},
	{
		"id": "357",
		"name": "Mhow Cantonment",
		"state": "Madhya Pradesh"
	},
	{
		"id": "1175",
		"name": "Mhowgaon",
		"state": "Madhya Pradesh"
	},
	{
		"id": "841",
		"name": "Mihijam",
		"state": "Jharkhand"
	},
	{
		"id": "57",
		"name": "Mira-Bhayandar",
		"state": "Maharashtra"
	},
	{
		"id": "1065",
		"name": "Mirganj",
		"state": "Bihar"
	},
	{
		"id": "361",
		"name": "Miryalaguda",
		"state": "Telangana"
	},
	{
		"id": "1219",
		"name": "Mirzapur",
		"state": "Uttar Pradesh"
	},
	{
		"id": "569",
		"name": "Modasa",
		"state": "Gujarat"
	},
	{
		"id": "130",
		"name": "Modinagar",
		"state": "Uttar Pradesh"
	},
	{
		"id": "227",
		"name": "Moga",
		"state": "Punjab"
	},
	{
		"id": "266",
		"name": "Mohali",
		"state": "Punjab"
	},
	{
		"id": "551",
		"name": "Mokameh",
		"state": "Bihar"
	},
	{
		"id": "892",
		"name": "Mokokchung",
		"state": "Nagaland"
	},
	{
		"id": "1167",
		"name": "Monoharpur",
		"state": "West Bengal"
	},
	{
		"id": "47",
		"name": "Moradabad",
		"state": "Uttar Pradesh"
	},
	{
		"id": "169",
		"name": "Morena",
		"state": "Madhya Pradesh"
	},
	{
		"id": "1099",
		"name": "Morinda, India",
		"state": "Punjab"
	},
	{
		"id": "836",
		"name": "Morshi",
		"state": "Maharashtra"
	},
	{
		"id": "177",
		"name": "Morvi",
		"state": "Gujarat"
	},
	{
		"id": "263",
		"name": "Motihari",
		"state": "Bihar"
	},
	{
		"id": "1123",
		"name": "Motipur",
		"state": "Bihar"
	},
	{
		"id": "1117",
		"name": "Mount Abu",
		"state": "Rajasthan"
	},
	{
		"id": "1010",
		"name": "Mudabidri",
		"state": "Karnataka"
	},
	{
		"id": "911",
		"name": "Mudalagi",
		"state": "Karnataka"
	},
	{
		"id": "944",
		"name": "Muddebihal",
		"state": "Karnataka"
	},
	{
		"id": "703",
		"name": "Mudhol",
		"state": "Karnataka"
	},
	{
		"id": "1145",
		"name": "Mukerian",
		"state": "Punjab"
	},
	{
		"id": "1004",
		"name": "Mukhed",
		"state": "Maharashtra"
	},
	{
		"id": "391",
		"name": "Muktsar",
		"state": "Punjab"
	},
	{
		"id": "1110",
		"name": "Mul",
		"state": "Maharashtra"
	},
	{
		"id": "687",
		"name": "Mulbagal",
		"state": "Karnataka"
	},
	{
		"id": "1144",
		"name": "Multai",
		"state": "Madhya Pradesh"
	},
	{
		"id": "1",
		"name": "Mumbai",
		"state": "Maharashtra"
	},
	{
		"id": "1180",
		"name": "Mundargi",
		"state": "Karnataka"
	},
	{
		"id": "909",
		"name": "Mundi",
		"state": "Madhya Pradesh"
	},
	{
		"id": "882",
		"name": "Mungeli",
		"state": "Chhattisgarh"
	},
	{
		"id": "162",
		"name": "Munger",
		"state": "Bihar"
	},
	{
		"id": "1087",
		"name": "Murliganj",
		"state": "Bihar"
	},
	{
		"id": "773",
		"name": "Murshidabad",
		"state": "West Bengal"
	},
	{
		"id": "748",
		"name": "Murtijapur",
		"state": "Maharashtra"
	},
	{
		"id": "155",
		"name": "Murwara (Katni)",
		"state": "Madhya Pradesh"
	},
	{
		"id": "826",
		"name": "Musabani",
		"state": "Jharkhand"
	},
	{
		"id": "924",
		"name": "Mussoorie",
		"state": "Uttarakhand"
	},
	{
		"id": "925",
		"name": "Muvattupuzha",
		"state": "Kerala"
	},
	{
		"id": "105",
		"name": "Muzaffarpur",
		"state": "Bihar"
	},
	{
		"id": "104",
		"name": "Mysore",
		"state": "Karnataka"
	},
	{
		"id": "260",
		"name": "Nabadwip",
		"state": "West Bengal"
	},
	{
		"id": "910",
		"name": "Nabarangapur",
		"state": "Odisha"
	},
	{
		"id": "460",
		"name": "Nabha",
		"state": "Punjab"
	},
	{
		"id": "992",
		"name": "Nadbai",
		"state": "Rajasthan"
	},
	{
		"id": "158",
		"name": "Nadiad",
		"state": "Gujarat"
	},
	{
		"id": "281",
		"name": "Nagaon",
		"state": "Assam"
	},
	{
		"id": "319",
		"name": "Nagapattinam",
		"state": "Tamil Nadu"
	},
	{
		"id": "1012",
		"name": "Nagar",
		"state": "Rajasthan"
	},
	{
		"id": "497",
		"name": "Nagari",
		"state": "Andhra Pradesh"
	},
	{
		"id": "983",
		"name": "Nagarkurnool",
		"state": "Telangana"
	},
	{
		"id": "331",
		"name": "Nagaur",
		"state": "Rajasthan"
	},
	{
		"id": "333",
		"name": "Nagda",
		"state": "Madhya Pradesh"
	},
	{
		"id": "153",
		"name": "Nagercoil",
		"state": "Tamil Nadu"
	},
	{
		"id": "349",
		"name": "Nagina",
		"state": "Uttar Pradesh"
	},
	{
		"id": "1115",
		"name": "Nagla",
		"state": "Uttarakhand"
	},
	{
		"id": "13",
		"name": "Nagpur",
		"state": "Maharashtra"
	},
	{
		"id": "932",
		"name": "Nahan",
		"state": "Himachal Pradesh"
	},
	{
		"id": "789",
		"name": "Naharlagun",
		"state": "Arunachal Pradesh"
	},
	{
		"id": "488",
		"name": "Naidupet",
		"state": "Andhra Pradesh"
	},
	{
		"id": "156",
		"name": "Naihati",
		"state": "West Bengal"
	},
	{
		"id": "730",
		"name": "Naila Janjgir",
		"state": "Chhattisgarh"
	},
	{
		"id": "718",
		"name": "Nainital",
		"state": "Uttarakhand"
	},
	{
		"id": "1101",
		"name": "Nainpur",
		"state": "Madhya Pradesh"
	},
	{
		"id": "369",
		"name": "Najibabad",
		"state": "Uttar Pradesh"
	},
	{
		"id": "771",
		"name": "Nakodar",
		"state": "Punjab"
	},
	{
		"id": "1097",
		"name": "Nakur",
		"state": "Uttar Pradesh"
	},
	{
		"id": "954",
		"name": "Nalbari",
		"state": "Assam"
	},
	{
		"id": "1149",
		"name": "Namagiripettai",
		"state": "Tamil Nadu"
	},
	{
		"id": "567",
		"name": "Namakkal",
		"state": "Tamil Nadu"
	},
	{
		"id": "73",
		"name": "Nanded-Waghala",
		"state": "Maharashtra"
	},
	{
		"id": "1204",
		"name": "Nandgaon",
		"state": "Maharashtra"
	},
	{
		"id": "686",
		"name": "Nandivaram-Guduvancheri",
		"state": "Tamil Nadu"
	},
	{
		"id": "708",
		"name": "Nandura",
		"state": "Maharashtra"
	},
	{
		"id": "296",
		"name": "Nandurbar",
		"state": "Maharashtra"
	},
	{
		"id": "168",
		"name": "Nandyal",
		"state": "Andhra Pradesh"
	},
	{
		"id": "721",
		"name": "Nangal",
		"state": "Punjab"
	},
	{
		"id": "610",
		"name": "Nanjangud",
		"state": "Karnataka"
	},
	{
		"id": "853",
		"name": "Nanjikottai",
		"state": "Tamil Nadu"
	},
	{
		"id": "639",
		"name": "Nanpara",
		"state": "Uttar Pradesh"
	},
	{
		"id": "527",
		"name": "Narasapuram",
		"state": "Andhra Pradesh"
	},
	{
		"id": "282",
		"name": "Narasaraopet",
		"state": "Andhra Pradesh"
	},
	{
		"id": "1096",
		"name": "Naraura",
		"state": "Uttar Pradesh"
	},
	{
		"id": "714",
		"name": "Narayanpet",
		"state": "Telangana"
	},
	{
		"id": "785",
		"name": "Nargund",
		"state": "Karnataka"
	},
	{
		"id": "628",
		"name": "Narkatiaganj",
		"state": "Bihar"
	},
	{
		"id": "1153",
		"name": "Narkhed",
		"state": "Maharashtra"
	},
	{
		"id": "421",
		"name": "Narnaul",
		"state": "Haryana"
	},
	{
		"id": "866",
		"name": "Narsinghgarh",
		"state": "Madhya Pradesh"
	},
	{
		"id": "1212",
		"name": "Narsinghgarh",
		"state": "Madhya Pradesh"
	},
	{
		"id": "830",
		"name": "Narsipatnam",
		"state": "Andhra Pradesh"
	},
	{
		"id": "498",
		"name": "Narwana",
		"state": "Haryana"
	},
	{
		"id": "25",
		"name": "Nashik",
		"state": "Maharashtra"
	},
	{
		"id": "607",
		"name": "Nasirabad",
		"state": "Rajasthan"
	},
	{
		"id": "1061",
		"name": "Natham",
		"state": "Tamil Nadu"
	},
	{
		"id": "710",
		"name": "Nathdwara",
		"state": "Rajasthan"
	},
	{
		"id": "633",
		"name": "Naugachhia",
		"state": "Bihar"
	},
	{
		"id": "847",
		"name": "Naugawan Sadat",
		"state": "Uttar Pradesh"
	},
	{
		"id": "831",
		"name": "Nautanwa",
		"state": "Uttar Pradesh"
	},
	{
		"id": "1037",
		"name": "Navalgund",
		"state": "Karnataka"
	},
	{
		"id": "209",
		"name": "Navsari",
		"state": "Gujarat"
	},
	{
		"id": "740",
		"name": "Nawabganj",
		"state": "Uttar Pradesh"
	},
	{
		"id": "341",
		"name": "Nawada",
		"state": "Bihar"
	},
	{
		"id": "663",
		"name": "Nawanshahr",
		"state": "Punjab"
	},
	{
		"id": "817",
		"name": "Nawapur",
		"state": "Maharashtra"
	},
	{
		"id": "519",
		"name": "Nedumangad",
		"state": "Kerala"
	},
	{
		"id": "786",
		"name": "Neem-Ka-Thana",
		"state": "Rajasthan"
	},
	{
		"id": "256",
		"name": "Neemuch",
		"state": "Madhya Pradesh"
	},
	{
		"id": "645",
		"name": "Nehtaur",
		"state": "Uttar Pradesh"
	},
	{
		"id": "770",
		"name": "Nelamangala",
		"state": "Karnataka"
	},
	{
		"id": "656",
		"name": "Nellikuppam",
		"state": "Tamil Nadu"
	},
	{
		"id": "81",
		"name": "Nellore",
		"state": "Andhra Pradesh"
	},
	{
		"id": "913",
		"name": "Nepanagar",
		"state": "Madhya Pradesh"
	},
	{
		"id": "143",
		"name": "New Delhi",
		"state": "Delhi"
	},
	{
		"id": "314",
		"name": "Neyveli (TS)",
		"state": "Tamil Nadu"
	},
	{
		"id": "445",
		"name": "Neyyattinkara",
		"state": "Kerala"
	},
	{
		"id": "691",
		"name": "Nidadavole",
		"state": "Andhra Pradesh"
	},
	{
		"id": "659",
		"name": "Nilambur",
		"state": "Kerala"
	},
	{
		"id": "788",
		"name": "Nilanga",
		"state": "Maharashtra"
	},
	{
		"id": "504",
		"name": "Nimbahera",
		"state": "Rajasthan"
	},
	{
		"id": "370",
		"name": "Nirmal",
		"state": "Telangana"
	},
	{
		"id": "1208",
		"name": "Niwai",
		"state": "Uttar Pradesh"
	},
	{
		"id": "1060",
		"name": "Niwari",
		"state": "Madhya Pradesh"
	},
	{
		"id": "114",
		"name": "Nizamabad",
		"state": "Telangana"
	},
	{
		"id": "621",
		"name": "Nohar",
		"state": "Rajasthan"
	},
	{
		"id": "64",
		"name": "Noida",
		"state": "Uttar Pradesh"
	},
	{
		"id": "496",
		"name": "Nokha",
		"state": "Rajasthan"
	},
	{
		"id": "974",
		"name": "Nokha",
		"state": "Bihar"
	},
	{
		"id": "935",
		"name": "Nongstoin",
		"state": "Meghalaya"
	},
	{
		"id": "746",
		"name": "Noorpur",
		"state": "Uttar Pradesh"
	},
	{
		"id": "520",
		"name": "North Lakhimpur",
		"state": "Assam"
	},
	{
		"id": "729",
		"name": "Nowgong",
		"state": "Madhya Pradesh"
	},
	{
		"id": "1130",
		"name": "Nowrozabad (Khodargama)",
		"state": "Madhya Pradesh"
	},
	{
		"id": "528",
		"name": "Nuzvid",
		"state": "Andhra Pradesh"
	},
	{
		"id": "1124",
		"name": "O' Valley",
		"state": "Tamil Nadu"
	},
	{
		"id": "1202",
		"name": "Obra",
		"state": "Uttar Pradesh"
	},
	{
		"id": "907",
		"name": "Oddanchatram",
		"state": "Tamil Nadu"
	},
	{
		"id": "167",
		"name": "Ongole",
		"state": "Andhra Pradesh"
	},
	{
		"id": "178",
		"name": "Orai",
		"state": "Uttar Pradesh"
	},
	{
		"id": "294",
		"name": "Osmanabad",
		"state": "Maharashtra"
	},
	{
		"id": "577",
		"name": "Ottappalam",
		"state": "Kerala"
	},
	{
		"id": "604",
		"name": "Ozar",
		"state": "Maharashtra"
	},
	{
		"id": "1072",
		"name": "P.N.Patti",
		"state": "Tamil Nadu"
	},
	{
		"id": "671",
		"name": "Pachora",
		"state": "Maharashtra"
	},
	{
		"id": "1161",
		"name": "Pachore",
		"state": "Madhya Pradesh"
	},
	{
		"id": "1095",
		"name": "Pacode",
		"state": "Tamil Nadu"
	},
	{
		"id": "1190",
		"name": "Padmanabhapuram",
		"state": "Tamil Nadu"
	},
	{
		"id": "741",
		"name": "Padra",
		"state": "Gujarat"
	},
	{
		"id": "679",
		"name": "Padrauna",
		"state": "Uttar Pradesh"
	},
	{
		"id": "810",
		"name": "Paithan",
		"state": "Maharashtra"
	},
	{
		"id": "790",
		"name": "Pakaur",
		"state": "Jharkhand"
	},
	{
		"id": "414",
		"name": "Palacole",
		"state": "Andhra Pradesh"
	},
	{
		"id": "1098",
		"name": "Palai",
		"state": "Kerala"
	},
	{
		"id": "172",
		"name": "Palakkad",
		"state": "Kerala"
	},
	{
		"id": "1073",
		"name": "Palampur",
		"state": "Himachal Pradesh"
	},
	{
		"id": "466",
		"name": "Palani",
		"state": "Tamil Nadu"
	},
	{
		"id": "258",
		"name": "Palanpur",
		"state": "Gujarat"
	},
	{
		"id": "620",
		"name": "Palasa Kasibugga",
		"state": "Andhra Pradesh"
	},
	{
		"id": "590",
		"name": "Palghar",
		"state": "Maharashtra"
	},
	{
		"id": "147",
		"name": "Pali",
		"state": "Rajasthan"
	},
	{
		"id": "1160",
		"name": "Pali",
		"state": "Madhya Pradesh"
	},
	{
		"id": "804",
		"name": "Palia Kalan",
		"state": "Uttar Pradesh"
	},
	{
		"id": "597",
		"name": "Palitana",
		"state": "Gujarat"
	},
	{
		"id": "908",
		"name": "Palladam",
		"state": "Tamil Nadu"
	},
	{
		"id": "1044",
		"name": "Pallapatti",
		"state": "Tamil Nadu"
	},
	{
		"id": "1170",
		"name": "Pallikonda",
		"state": "Tamil Nadu"
	},
	{
		"id": "257",
		"name": "Palwal",
		"state": "Haryana"
	},
	{
		"id": "456",
		"name": "Palwancha",
		"state": "Telangana"
	},
	{
		"id": "1022",
		"name": "Panagar",
		"state": "Madhya Pradesh"
	},
	{
		"id": "1016",
		"name": "Panagudi",
		"state": "Tamil Nadu"
	},
	{
		"id": "336",
		"name": "Panaji",
		"state": "Goa"
	},
	{
		"id": "1207",
		"name": "Panamattom",
		"state": "Kerala"
	},
	{
		"id": "163",
		"name": "Panchkula",
		"state": "Haryana"
	},
	{
		"id": "1120",
		"name": "Panchla",
		"state": "West Bengal"
	},
	{
		"id": "975",
		"name": "PandUrban Agglomeration",
		"state": "West Bengal"
	},
	{
		"id": "989",
		"name": "Pandharkaoda",
		"state": "Maharashtra"
	},
	{
		"id": "360",
		"name": "Pandharpur",
		"state": "Maharashtra"
	},
	{
		"id": "724",
		"name": "Pandhurna",
		"state": "Madhya Pradesh"
	},
	{
		"id": "119",
		"name": "Panipat",
		"state": "Haryana"
	},
	{
		"id": "594",
		"name": "Panna",
		"state": "Madhya Pradesh"
	},
	{
		"id": "1164",
		"name": "Panniyannur",
		"state": "Kerala"
	},
	{
		"id": "565",
		"name": "Panruti",
		"state": "Tamil Nadu"
	},
	{
		"id": "165",
		"name": "Panvel",
		"state": "Maharashtra"
	},
	{
		"id": "839",
		"name": "Pappinisseri",
		"state": "Kerala"
	},
	{
		"id": "427",
		"name": "Paradip",
		"state": "Odisha"
	},
	{
		"id": "387",
		"name": "Paramakudi",
		"state": "Tamil Nadu"
	},
	{
		"id": "1162",
		"name": "Parangipettai",
		"state": "Tamil Nadu"
	},
	{
		"id": "1150",
		"name": "Parasi",
		"state": "Uttar Pradesh"
	},
	{
		"id": "747",
		"name": "Paravoor",
		"state": "Kerala"
	},
	{
		"id": "139",
		"name": "Parbhani",
		"state": "Maharashtra"
	},
	{
		"id": "1020",
		"name": "Pardi",
		"state": "Gujarat"
	},
	{
		"id": "695",
		"name": "Parlakhemundi",
		"state": "Odisha"
	},
	{
		"id": "368",
		"name": "Parli",
		"state": "Maharashtra"
	},
	{
		"id": "931",
		"name": "Partur",
		"state": "Maharashtra"
	},
	{
		"id": "625",
		"name": "Parvathipuram",
		"state": "Andhra Pradesh"
	},
	{
		"id": "919",
		"name": "Pasan",
		"state": "Madhya Pradesh"
	},
	{
		"id": "893",
		"name": "Paschim Punropara",
		"state": "West Bengal"
	},
	{
		"id": "1122",
		"name": "Pasighat",
		"state": "Arunachal Pradesh"
	},
	{
		"id": "290",
		"name": "Patan",
		"state": "Gujarat"
	},
	{
		"id": "753",
		"name": "Pathanamthitta",
		"state": "Kerala"
	},
	{
		"id": "199",
		"name": "Pathankot",
		"state": "Punjab"
	},
	{
		"id": "1093",
		"name": "Pathardi",
		"state": "Maharashtra"
	},
	{
		"id": "878",
		"name": "Pathri",
		"state": "Maharashtra"
	},
	{
		"id": "27",
		"name": "Patiala",
		"state": "Punjab"
	},
	{
		"id": "14",
		"name": "Patna",
		"state": "Bihar"
	},
	{
		"id": "875",
		"name": "Patratu",
		"state": "Jharkhand"
	},
	{
		"id": "852",
		"name": "Pattamundai",
		"state": "Odisha"
	},
	{
		"id": "812",
		"name": "Patti",
		"state": "Punjab"
	},
	{
		"id": "1116",
		"name": "Pattran",
		"state": "Punjab"
	},
	{
		"id": "480",
		"name": "Pattukkottai",
		"state": "Tamil Nadu"
	},
	{
		"id": "1173",
		"name": "Patur",
		"state": "Maharashtra"
	},
	{
		"id": "1103",
		"name": "Pauni",
		"state": "Maharashtra"
	},
	{
		"id": "1035",
		"name": "Pauri",
		"state": "Uttarakhand"
	},
	{
		"id": "949",
		"name": "Pavagada",
		"state": "Karnataka"
	},
	{
		"id": "916",
		"name": "Pedana",
		"state": "Andhra Pradesh"
	},
	{
		"id": "669",
		"name": "Peddapuram",
		"state": "Andhra Pradesh"
	},
	{
		"id": "837",
		"name": "Pehowa",
		"state": "Haryana"
	},
	{
		"id": "904",
		"name": "Pen",
		"state": "Maharashtra"
	},
	{
		"id": "763",
		"name": "Perambalur",
		"state": "Tamil Nadu"
	},
	{
		"id": "1156",
		"name": "Peravurani",
		"state": "Tamil Nadu"
	},
	{
		"id": "762",
		"name": "Peringathur",
		"state": "Kerala"
	},
	{
		"id": "678",
		"name": "Perinthalmanna",
		"state": "Kerala"
	},
	{
		"id": "711",
		"name": "Periyakulam",
		"state": "Tamil Nadu"
	},
	{
		"id": "877",
		"name": "Periyasemur",
		"state": "Tamil Nadu"
	},
	{
		"id": "717",
		"name": "Pernampattu",
		"state": "Tamil Nadu"
	},
	{
		"id": "990",
		"name": "Perumbavoor",
		"state": "Kerala"
	},
	{
		"id": "606",
		"name": "Petlad",
		"state": "Gujarat"
	},
	{
		"id": "323",
		"name": "Phagwara",
		"state": "Punjab"
	},
	{
		"id": "675",
		"name": "Phalodi",
		"state": "Rajasthan"
	},
	{
		"id": "608",
		"name": "Phaltan",
		"state": "Maharashtra"
	},
	{
		"id": "1112",
		"name": "Phillaur",
		"state": "Punjab"
	},
	{
		"id": "828",
		"name": "Phulabani",
		"state": "Odisha"
	},
	{
		"id": "1137",
		"name": "Phulera",
		"state": "Rajasthan"
	},
	{
		"id": "1158",
		"name": "Phulpur",
		"state": "Uttar Pradesh"
	},
	{
		"id": "191",
		"name": "Phusro",
		"state": "Jharkhand"
	},
	{
		"id": "966",
		"name": "Pihani",
		"state": "Uttar Pradesh"
	},
	{
		"id": "728",
		"name": "Pilani",
		"state": "Rajasthan"
	},
	{
		"id": "835",
		"name": "Pilibanga",
		"state": "Rajasthan"
	},
	{
		"id": "264",
		"name": "Pilibhit",
		"state": "Uttar Pradesh"
	},
	{
		"id": "469",
		"name": "Pilkhuwa",
		"state": "Uttar Pradesh"
	},
	{
		"id": "1171",
		"name": "Pindwara",
		"state": "Rajasthan"
	},
	{
		"id": "917",
		"name": "Pinjore",
		"state": "Haryana"
	},
	{
		"id": "851",
		"name": "Pipar City",
		"state": "Rajasthan"
	},
	{
		"id": "680",
		"name": "Pipariya",
		"state": "Madhya Pradesh"
	},
	{
		"id": "1201",
		"name": "Piriyapatna",
		"state": "Karnataka"
	},
	{
		"id": "1009",
		"name": "Piro",
		"state": "Bihar"
	},
	{
		"id": "459",
		"name": "Pithampur",
		"state": "Madhya Pradesh"
	},
	{
		"id": "615",
		"name": "Pithapuram",
		"state": "Andhra Pradesh"
	},
	{
		"id": "673",
		"name": "Pithoragarh",
		"state": "Uttarakhand"
	},
	{
		"id": "254",
		"name": "Pollachi",
		"state": "Tamil Nadu"
	},
	{
		"id": "1015",
		"name": "Polur",
		"state": "Tamil Nadu"
	},
	{
		"id": "80",
		"name": "Pondicherry",
		"state": "Puducherry"
	},
	{
		"id": "375",
		"name": "Ponnani",
		"state": "Kerala"
	},
	{
		"id": "1047",
		"name": "Ponneri",
		"state": "Tamil Nadu"
	},
	{
		"id": "540",
		"name": "Ponnur",
		"state": "Andhra Pradesh"
	},
	{
		"id": "171",
		"name": "Porbandar",
		"state": "Gujarat"
	},
	{
		"id": "846",
		"name": "Porsa",
		"state": "Madhya Pradesh"
	},
	{
		"id": "334",
		"name": "Port Blair",
		"state": "Andaman and Nicobar Islands"
	},
	{
		"id": "1071",
		"name": "Powayan",
		"state": "Uttar Pradesh"
	},
	{
		"id": "1114",
		"name": "Prantij",
		"state": "Rajasthan"
	},
	{
		"id": "801",
		"name": "Pratapgarh",
		"state": "Rajasthan"
	},
	{
		"id": "981",
		"name": "Pratapgarh",
		"state": "Tripura"
	},
	{
		"id": "1104",
		"name": "Prithvipur",
		"state": "Madhya Pradesh"
	},
	{
		"id": "228",
		"name": "Proddatur",
		"state": "Andhra Pradesh"
	},
	{
		"id": "304",
		"name": "Pudukkottai",
		"state": "Tamil Nadu"
	},
	{
		"id": "1163",
		"name": "Pudupattinam",
		"state": "Tamil Nadu"
	},
	{
		"id": "1189",
		"name": "Pukhrayan",
		"state": "Uttar Pradesh"
	},
	{
		"id": "783",
		"name": "Pulgaon",
		"state": "Maharashtra"
	},
	{
		"id": "843",
		"name": "Puliyankudi",
		"state": "Tamil Nadu"
	},
	{
		"id": "653",
		"name": "Punalur",
		"state": "Kerala"
	},
	{
		"id": "723",
		"name": "Punch",
		"state": "Jammu and Kashmir"
	},
	{
		"id": "8",
		"name": "Pune",
		"state": "Maharashtra"
	},
	{
		"id": "682",
		"name": "Punganur",
		"state": "Andhra Pradesh"
	},
	{
		"id": "1181",
		"name": "Punjaipugalur",
		"state": "Tamil Nadu"
	},
	{
		"id": "769",
		"name": "Puranpur",
		"state": "Uttar Pradesh"
	},
	{
		"id": "211",
		"name": "Puri",
		"state": "Odisha"
	},
	{
		"id": "842",
		"name": "Purna",
		"state": "Maharashtra"
	},
	{
		"id": "174",
		"name": "Purnia",
		"state": "Bihar"
	},
	{
		"id": "1063",
		"name": "PurqUrban Agglomerationzi",
		"state": "Uttar Pradesh"
	},
	{
		"id": "289",
		"name": "Purulia",
		"state": "West Bengal"
	},
	{
		"id": "1147",
		"name": "Purwa",
		"state": "Uttar Pradesh"
	},
	{
		"id": "467",
		"name": "Pusad",
		"state": "Maharashtra"
	},
	{
		"id": "1195",
		"name": "Puthuppally",
		"state": "Kerala"
	},
	{
		"id": "643",
		"name": "Puttur",
		"state": "Karnataka"
	},
	{
		"id": "920",
		"name": "Puttur",
		"state": "Andhra Pradesh"
	},
	{
		"id": "1127",
		"name": "Qadian",
		"state": "Punjab"
	},
	{
		"id": "164",
		"name": "Raayachuru",
		"state": "Karnataka"
	},
	{
		"id": "452",
		"name": "Rabkavi Banhatti",
		"state": "Karnataka"
	},
	{
		"id": "872",
		"name": "Radhanpur",
		"state": "Gujarat"
	},
	{
		"id": "196",
		"name": "Rae Bareli",
		"state": "Uttar Pradesh"
	},
	{
		"id": "1030",
		"name": "Rafiganj",
		"state": "Bihar"
	},
	{
		"id": "631",
		"name": "Raghogarh-Vijaypur",
		"state": "Madhya Pradesh"
	},
	{
		"id": "108",
		"name": "Raghunathganj",
		"state": "West Bengal"
	},
	{
		"id": "1125",
		"name": "Raghunathpur",
		"state": "West Bengal"
	},
	{
		"id": "1021",
		"name": "Rahatgarh",
		"state": "Madhya Pradesh"
	},
	{
		"id": "811",
		"name": "Rahuri",
		"state": "Maharashtra"
	},
	{
		"id": "190",
		"name": "Raiganj",
		"state": "West Bengal"
	},
	{
		"id": "283",
		"name": "Raigarh",
		"state": "Chhattisgarh"
	},
	{
		"id": "1034",
		"name": "Raikot",
		"state": "Punjab"
	},
	{
		"id": "36",
		"name": "Raipur",
		"state": "Chhattisgarh"
	},
	{
		"id": "1128",
		"name": "Rairangpur",
		"state": "Odisha"
	},
	{
		"id": "794",
		"name": "Raisen",
		"state": "Madhya Pradesh"
	},
	{
		"id": "961",
		"name": "Raisinghnagar",
		"state": "Rajasthan"
	},
	{
		"id": "693",
		"name": "Rajagangapur",
		"state": "Odisha"
	},
	{
		"id": "95",
		"name": "Rajahmundry",
		"state": "Andhra Pradesh"
	},
	{
		"id": "941",
		"name": "Rajakhera",
		"state": "Rajasthan"
	},
	{
		"id": "1092",
		"name": "Rajaldesar",
		"state": "Rajasthan"
	},
	{
		"id": "1068",
		"name": "Rajam",
		"state": "Andhra Pradesh"
	},
	{
		"id": "303",
		"name": "Rajampet",
		"state": "Andhra Pradesh"
	},
	{
		"id": "268",
		"name": "Rajapalayam",
		"state": "Tamil Nadu"
	},
	{
		"id": "716",
		"name": "Rajauri",
		"state": "Jammu and Kashmir"
	},
	{
		"id": "1053",
		"name": "Rajgarh",
		"state": "Madhya Pradesh"
	},
	{
		"id": "1029",
		"name": "Rajgarh (Alwar)",
		"state": "Rajasthan"
	},
	{
		"id": "601",
		"name": "Rajgarh (Churu)",
		"state": "Rajasthan"
	},
	{
		"id": "832",
		"name": "Rajgir",
		"state": "Bihar"
	},
	{
		"id": "22",
		"name": "Rajkot",
		"state": "Gujarat"
	},
	{
		"id": "240",
		"name": "Rajnandgaon",
		"state": "Chhattisgarh"
	},
	{
		"id": "806",
		"name": "Rajpipla",
		"state": "Gujarat"
	},
	{
		"id": "392",
		"name": "Rajpura",
		"state": "Punjab"
	},
	{
		"id": "562",
		"name": "Rajsamand",
		"state": "Rajasthan"
	},
	{
		"id": "862",
		"name": "Rajula",
		"state": "Gujarat"
	},
	{
		"id": "1008",
		"name": "Rajura",
		"state": "Maharashtra"
	},
	{
		"id": "719",
		"name": "Ramachandrapuram",
		"state": "Andhra Pradesh"
	},
	{
		"id": "146",
		"name": "Ramagundam",
		"state": "Telangana"
	},
	{
		"id": "402",
		"name": "Ramanagaram",
		"state": "Karnataka"
	},
	{
		"id": "499",
		"name": "Ramanathapuram",
		"state": "Tamil Nadu"
	},
	{
		"id": "782",
		"name": "Ramdurg",
		"state": "Karnataka"
	},
	{
		"id": "755",
		"name": "Rameshwaram",
		"state": "Tamil Nadu"
	},
	{
		"id": "895",
		"name": "Ramganj Mandi",
		"state": "Rajasthan"
	},
	{
		"id": "298",
		"name": "Ramgarh",
		"state": "Jharkhand"
	},
	{
		"id": "661",
		"name": "Ramnagar",
		"state": "Uttarakhand"
	},
	{
		"id": "749",
		"name": "Ramnagar",
		"state": "Bihar"
	},
	{
		"id": "938",
		"name": "Ramngarh",
		"state": "Rajasthan"
	},
	{
		"id": "127",
		"name": "Rampur",
		"state": "Uttar Pradesh"
	},
	{
		"id": "1032",
		"name": "Rampur Maniharan",
		"state": "Uttar Pradesh"
	},
	{
		"id": "677",
		"name": "Rampura Phul",
		"state": "Punjab"
	},
	{
		"id": "609",
		"name": "Rampurhat",
		"state": "West Bengal"
	},
	{
		"id": "1105",
		"name": "Ramtek",
		"state": "Maharashtra"
	},
	{
		"id": "236",
		"name": "Ranaghat",
		"state": "West Bengal"
	},
	{
		"id": "915",
		"name": "Ranavav",
		"state": "Gujarat"
	},
	{
		"id": "51",
		"name": "Ranchi",
		"state": "Jharkhand"
	},
	{
		"id": "312",
		"name": "Ranebennuru",
		"state": "Karnataka"
	},
	{
		"id": "1025",
		"name": "Rangia",
		"state": "Assam"
	},
	{
		"id": "1159",
		"name": "Rania",
		"state": "Haryana"
	},
	{
		"id": "364",
		"name": "Ranibennur",
		"state": "Karnataka"
	},
	{
		"id": "135",
		"name": "Ranipet",
		"state": "Tamil Nadu"
	},
	{
		"id": "1083",
		"name": "Rapar",
		"state": "Gujarat"
	},
	{
		"id": "660",
		"name": "Rasipuram",
		"state": "Tamil Nadu"
	},
	{
		"id": "926",
		"name": "Rasra",
		"state": "Uttar Pradesh"
	},
	{
		"id": "491",
		"name": "Ratangarh",
		"state": "Rajasthan"
	},
	{
		"id": "559",
		"name": "Rath",
		"state": "Uttar Pradesh"
	},
	{
		"id": "1057",
		"name": "Ratia",
		"state": "Haryana"
	},
	{
		"id": "129",
		"name": "Ratlam",
		"state": "Madhya Pradesh"
	},
	{
		"id": "451",
		"name": "Ratnagiri",
		"state": "Maharashtra"
	},
	{
		"id": "1166",
		"name": "Rau",
		"state": "Madhya Pradesh"
	},
	{
		"id": "84",
		"name": "Raurkela",
		"state": "Odisha"
	},
	{
		"id": "1001",
		"name": "Raver",
		"state": "Maharashtra"
	},
	{
		"id": "808",
		"name": "Rawatbhata",
		"state": "Rajasthan"
	},
	{
		"id": "940",
		"name": "Rawatsar",
		"state": "Rajasthan"
	},
	{
		"id": "715",
		"name": "Raxaul Bazar",
		"state": "Bihar"
	},
	{
		"id": "438",
		"name": "Rayachoti",
		"state": "Andhra Pradesh"
	},
	{
		"id": "570",
		"name": "Rayadurg",
		"state": "Andhra Pradesh"
	},
	{
		"id": "538",
		"name": "Rayagada",
		"state": "Odisha"
	},
	{
		"id": "1088",
		"name": "Reengus",
		"state": "Rajasthan"
	},
	{
		"id": "1006",
		"name": "Rehli",
		"state": "Madhya Pradesh"
	},
	{
		"id": "1055",
		"name": "Renigunta",
		"state": "Andhra Pradesh"
	},
	{
		"id": "471",
		"name": "Renukoot",
		"state": "Uttar Pradesh"
	},
	{
		"id": "1118",
		"name": "Reoti",
		"state": "Uttar Pradesh"
	},
	{
		"id": "702",
		"name": "Repalle",
		"state": "Andhra Pradesh"
	},
	{
		"id": "822",
		"name": "Revelganj",
		"state": "Bihar"
	},
	{
		"id": "187",
		"name": "Rewa",
		"state": "Madhya Pradesh"
	},
	{
		"id": "330",
		"name": "Rewari",
		"state": "Haryana"
	},
	{
		"id": "406",
		"name": "Rishikesh",
		"state": "Uttarakhand"
	},
	{
		"id": "968",
		"name": "Risod",
		"state": "Maharashtra"
	},
	{
		"id": "870",
		"name": "Robertsganj",
		"state": "Uttar Pradesh"
	},
	{
		"id": "212",
		"name": "Robertson Pet",
		"state": "Karnataka"
	},
	{
		"id": "118",
		"name": "Rohtak",
		"state": "Haryana"
	},
	{
		"id": "1138",
		"name": "Ron",
		"state": "Karnataka"
	},
	{
		"id": "284",
		"name": "Roorkee",
		"state": "Uttarakhand"
	},
	{
		"id": "969",
		"name": "Rosera",
		"state": "Bihar"
	},
	{
		"id": "776",
		"name": "Rudauli",
		"state": "Uttar Pradesh"
	},
	{
		"id": "367",
		"name": "Rudrapur",
		"state": "Uttarakhand"
	},
	{
		"id": "986",
		"name": "Rudrapur",
		"state": "Uttar Pradesh"
	},
	{
		"id": "632",
		"name": "Rupnagar",
		"state": "Punjab"
	},
	{
		"id": "999",
		"name": "SUrban Agglomerationr",
		"state": "Uttar Pradesh"
	},
	{
		"id": "823",
		"name": "Sabalgarh",
		"state": "Madhya Pradesh"
	},
	{
		"id": "879",
		"name": "Sadabad",
		"state": "Uttar Pradesh"
	},
	{
		"id": "1185",
		"name": "Sadalagi",
		"state": "Karnataka"
	},
	{
		"id": "784",
		"name": "Sadasivpet",
		"state": "Telangana"
	},
	{
		"id": "1041",
		"name": "Sadri",
		"state": "Rajasthan"
	},
	{
		"id": "267",
		"name": "Sadulpur",
		"state": "Rajasthan"
	},
	{
		"id": "1111",
		"name": "Sadulshahar",
		"state": "Rajasthan"
	},
	{
		"id": "967",
		"name": "Safidon",
		"state": "Haryana"
	},
	{
		"id": "1108",
		"name": "Safipur",
		"state": "Uttar Pradesh"
	},
	{
		"id": "115",
		"name": "Sagar",
		"state": "Madhya Pradesh"
	},
	{
		"id": "614",
		"name": "Sagara",
		"state": "Karnataka"
	},
	{
		"id": "894",
		"name": "Sagwara",
		"state": "Rajasthan"
	},
	{
		"id": "60",
		"name": "Saharanpur",
		"state": "Uttar Pradesh"
	},
	{
		"id": "217",
		"name": "Saharsa",
		"state": "Bihar"
	},
	{
		"id": "1102",
		"name": "Sahaspur",
		"state": "Uttar Pradesh"
	},
	{
		"id": "535",
		"name": "Sahaswan",
		"state": "Uttar Pradesh"
	},
	{
		"id": "1178",
		"name": "Sahawar",
		"state": "Uttar Pradesh"
	},
	{
		"id": "400",
		"name": "Sahibganj",
		"state": "Jharkhand"
	},
	{
		"id": "1026",
		"name": "Sahjanwa",
		"state": "Uttar Pradesh"
	},
	{
		"id": "1140",
		"name": "Saidpur",
		"state": "Uttar Pradesh"
	},
	{
		"id": "1192",
		"name": "Saiha",
		"state": "Mizoram"
	},
	{
		"id": "736",
		"name": "Sailu",
		"state": "Maharashtra"
	},
	{
		"id": "742",
		"name": "Sainthia",
		"state": "West Bengal"
	},
	{
		"id": "1080",
		"name": "Sakaleshapura",
		"state": "Karnataka"
	},
	{
		"id": "1184",
		"name": "Sakti",
		"state": "Chhattisgarh"
	},
	{
		"id": "979",
		"name": "Salaya",
		"state": "Gujarat"
	},
	{
		"id": "55",
		"name": "Salem",
		"state": "Tamil Nadu"
	},
	{
		"id": "638",
		"name": "Salur",
		"state": "Andhra Pradesh"
	},
	{
		"id": "912",
		"name": "Samalkha",
		"state": "Haryana"
	},
	{
		"id": "579",
		"name": "Samalkot",
		"state": "Andhra Pradesh"
	},
	{
		"id": "657",
		"name": "Samana",
		"state": "Punjab"
	},
	{
		"id": "501",
		"name": "Samastipur",
		"state": "Bihar"
	},
	{
		"id": "186",
		"name": "Sambalpur",
		"state": "Odisha"
	},
	{
		"id": "157",
		"name": "Sambhal",
		"state": "Uttar Pradesh"
	},
	{
		"id": "1113",
		"name": "Sambhar",
		"state": "Rajasthan"
	},
	{
		"id": "1019",
		"name": "Samdhan",
		"state": "Uttar Pradesh"
	},
	{
		"id": "1183",
		"name": "Samthar",
		"state": "Uttar Pradesh"
	},
	{
		"id": "861",
		"name": "Sanand",
		"state": "Gujarat"
	},
	{
		"id": "820",
		"name": "Sanawad",
		"state": "Madhya Pradesh"
	},
	{
		"id": "1007",
		"name": "Sanchore",
		"state": "Rajasthan"
	},
	{
		"id": "1076",
		"name": "Sandi",
		"state": "Uttar Pradesh"
	},
	{
		"id": "636",
		"name": "Sandila",
		"state": "Uttar Pradesh"
	},
	{
		"id": "963",
		"name": "Sanduru",
		"state": "Karnataka"
	},
	{
		"id": "503",
		"name": "Sangamner",
		"state": "Maharashtra"
	},
	{
		"id": "545",
		"name": "Sangareddy",
		"state": "Telangana"
	},
	{
		"id": "809",
		"name": "Sangaria",
		"state": "Rajasthan"
	},
	{
		"id": "77",
		"name": "Sangli",
		"state": "Maharashtra"
	},
	{
		"id": "948",
		"name": "Sangole",
		"state": "Maharashtra"
	},
	{
		"id": "411",
		"name": "Sangrur",
		"state": "Punjab"
	},
	{
		"id": "449",
		"name": "Sankarankovil",
		"state": "Tamil Nadu"
	},
	{
		"id": "970",
		"name": "Sankari",
		"state": "Tamil Nadu"
	},
	{
		"id": "858",
		"name": "Sankeshwara",
		"state": "Karnataka"
	},
	{
		"id": "224",
		"name": "Santipur",
		"state": "West Bengal"
	},
	{
		"id": "869",
		"name": "Sarangpur",
		"state": "Madhya Pradesh"
	},
	{
		"id": "396",
		"name": "Sardarshahar",
		"state": "Rajasthan"
	},
	{
		"id": "640",
		"name": "Sardhana",
		"state": "Uttar Pradesh"
	},
	{
		"id": "352",
		"name": "Sarni",
		"state": "Madhya Pradesh"
	},
	{
		"id": "1215",
		"name": "Sarsod",
		"state": "Haryana"
	},
	{
		"id": "232",
		"name": "Sasaram",
		"state": "Bihar"
	},
	{
		"id": "985",
		"name": "Sasvad",
		"state": "Maharashtra"
	},
	{
		"id": "856",
		"name": "Satana",
		"state": "Maharashtra"
	},
	{
		"id": "307",
		"name": "Satara",
		"state": "Maharashtra"
	},
	{
		"id": "834",
		"name": "Sathyamangalam",
		"state": "Tamil Nadu"
	},
	{
		"id": "148",
		"name": "Satna",
		"state": "Madhya Pradesh"
	},
	{
		"id": "602",
		"name": "Sattenapalle",
		"state": "Andhra Pradesh"
	},
	{
		"id": "886",
		"name": "Sattur",
		"state": "Tamil Nadu"
	},
	{
		"id": "385",
		"name": "Saunda",
		"state": "Jharkhand"
	},
	{
		"id": "752",
		"name": "Saundatti-Yellamma",
		"state": "Karnataka"
	},
	{
		"id": "1046",
		"name": "Sausar",
		"state": "Madhya Pradesh"
	},
	{
		"id": "797",
		"name": "Savanur",
		"state": "Karnataka"
	},
	{
		"id": "426",
		"name": "Savarkundla",
		"state": "Gujarat"
	},
	{
		"id": "984",
		"name": "Savner",
		"state": "Maharashtra"
	},
	{
		"id": "325",
		"name": "Sawai Madhopur",
		"state": "Rajasthan"
	},
	{
		"id": "1089",
		"name": "Sawantwadi",
		"state": "Maharashtra"
	},
	{
		"id": "883",
		"name": "Sedam",
		"state": "Karnataka"
	},
	{
		"id": "356",
		"name": "Sehore",
		"state": "Madhya Pradesh"
	},
	{
		"id": "634",
		"name": "Sendhwa",
		"state": "Madhya Pradesh"
	},
	{
		"id": "649",
		"name": "Seohara",
		"state": "Uttar Pradesh"
	},
	{
		"id": "363",
		"name": "Seoni",
		"state": "Madhya Pradesh"
	},
	{
		"id": "997",
		"name": "Seoni-Malwa",
		"state": "Madhya Pradesh"
	},
	{
		"id": "473",
		"name": "Shahabad",
		"state": "Karnataka"
	},
	{
		"id": "461",
		"name": "Shahabad, Hardoi",
		"state": "Uttar Pradesh"
	},
	{
		"id": "864",
		"name": "Shahabad, Rampur",
		"state": "Uttar Pradesh"
	},
	{
		"id": "626",
		"name": "Shahade",
		"state": "Maharashtra"
	},
	{
		"id": "768",
		"name": "Shahbad",
		"state": "Haryana"
	},
	{
		"id": "407",
		"name": "Shahdol",
		"state": "Madhya Pradesh"
	},
	{
		"id": "1038",
		"name": "Shahganj",
		"state": "Uttar Pradesh"
	},
	{
		"id": "110",
		"name": "Shahjahanpur",
		"state": "Uttar Pradesh"
	},
	{
		"id": "744",
		"name": "Shahpur",
		"state": "Karnataka"
	},
	{
		"id": "946",
		"name": "Shahpura",
		"state": "Rajasthan"
	},
	{
		"id": "959",
		"name": "Shahpura",
		"state": "Rajasthan"
	},
	{
		"id": "537",
		"name": "Shajapur",
		"state": "Madhya Pradesh"
	},
	{
		"id": "1141",
		"name": "Shamgarh",
		"state": "Madhya Pradesh"
	},
	{
		"id": "362",
		"name": "Shamli",
		"state": "Uttar Pradesh"
	},
	{
		"id": "973",
		"name": "Shamsabad, Agra",
		"state": "Uttar Pradesh"
	},
	{
		"id": "1064",
		"name": "Shamsabad, Farrukhabad",
		"state": "Uttar Pradesh"
	},
	{
		"id": "591",
		"name": "Shegaon",
		"state": "Maharashtra"
	},
	{
		"id": "694",
		"name": "Sheikhpura",
		"state": "Bihar"
	},
	{
		"id": "1155",
		"name": "Shendurjana",
		"state": "Maharashtra"
	},
	{
		"id": "980",
		"name": "Shenkottai",
		"state": "Tamil Nadu"
	},
	{
		"id": "1033",
		"name": "Sheoganj",
		"state": "Rajasthan"
	},
	{
		"id": "1148",
		"name": "Sheohar",
		"state": "Bihar"
	},
	{
		"id": "532",
		"name": "Sheopur",
		"state": "Madhya Pradesh"
	},
	{
		"id": "857",
		"name": "Sherghati",
		"state": "Bihar"
	},
	{
		"id": "589",
		"name": "Sherkot",
		"state": "Uttar Pradesh"
	},
	{
		"id": "1043",
		"name": "Shiggaon",
		"state": "Karnataka"
	},
	{
		"id": "884",
		"name": "Shikaripur",
		"state": "Karnataka"
	},
	{
		"id": "844",
		"name": "Shikarpur, Bulandshahr",
		"state": "Uttar Pradesh"
	},
	{
		"id": "372",
		"name": "Shikohabad",
		"state": "Uttar Pradesh"
	},
	{
		"id": "132",
		"name": "Shillong",
		"state": "Meghalaya"
	},
	{
		"id": "237",
		"name": "Shimla",
		"state": "Himachal Pradesh"
	},
	{
		"id": "998",
		"name": "Shirdi",
		"state": "Maharashtra"
	},
	{
		"id": "509",
		"name": "Shirpur-Warwade",
		"state": "Maharashtra"
	},
	{
		"id": "978",
		"name": "Shirur",
		"state": "Maharashtra"
	},
	{
		"id": "1172",
		"name": "Shishgarh",
		"state": "Uttar Pradesh"
	},
	{
		"id": "128",
		"name": "Shivamogga",
		"state": "Karnataka"
	},
	{
		"id": "235",
		"name": "Shivpuri",
		"state": "Madhya Pradesh"
	},
	{
		"id": "1139",
		"name": "Sholavandan",
		"state": "Tamil Nadu"
	},
	{
		"id": "988",
		"name": "Sholingur",
		"state": "Tamil Nadu"
	},
	{
		"id": "709",
		"name": "Shoranur",
		"state": "Kerala"
	},
	{
		"id": "994",
		"name": "Shrigonda",
		"state": "Maharashtra"
	},
	{
		"id": "366",
		"name": "Shrirampur",
		"state": "Maharashtra"
	},
	{
		"id": "1059",
		"name": "Shrirangapattana",
		"state": "Karnataka"
	},
	{
		"id": "704",
		"name": "Shujalpur",
		"state": "Madhya Pradesh"
	},
	{
		"id": "743",
		"name": "Siana",
		"state": "Uttar Pradesh"
	},
	{
		"id": "575",
		"name": "Sibsagar",
		"state": "Assam"
	},
	{
		"id": "505",
		"name": "Siddipet",
		"state": "Telangana"
	},
	{
		"id": "667",
		"name": "Sidhi",
		"state": "Madhya Pradesh"
	},
	{
		"id": "534",
		"name": "Sidhpur",
		"state": "Gujarat"
	},
	{
		"id": "722",
		"name": "Sidlaghatta",
		"state": "Karnataka"
	},
	{
		"id": "655",
		"name": "Sihor",
		"state": "Gujarat"
	},
	{
		"id": "758",
		"name": "Sihora",
		"state": "Madhya Pradesh"
	},
	{
		"id": "1135",
		"name": "Sikanderpur",
		"state": "Uttar Pradesh"
	},
	{
		"id": "757",
		"name": "Sikandra Rao",
		"state": "Uttar Pradesh"
	},
	{
		"id": "453",
		"name": "Sikandrabad",
		"state": "Uttar Pradesh"
	},
	{
		"id": "180",
		"name": "Sikar",
		"state": "Rajasthan"
	},
	{
		"id": "1186",
		"name": "Silao",
		"state": "Bihar"
	},
	{
		"id": "1106",
		"name": "Silapathar",
		"state": "Assam"
	},
	{
		"id": "185",
		"name": "Silchar",
		"state": "Assam"
	},
	{
		"id": "24",
		"name": "Siliguri",
		"state": "West Bengal"
	},
	{
		"id": "690",
		"name": "Sillod",
		"state": "Maharashtra"
	},
	{
		"id": "1129",
		"name": "Silvassa",
		"state": "Dadra and Nagar Haveli"
	},
	{
		"id": "825",
		"name": "Simdega",
		"state": "Jharkhand"
	},
	{
		"id": "1075",
		"name": "Sindagi",
		"state": "Karnataka"
	},
	{
		"id": "962",
		"name": "Sindhagi",
		"state": "Karnataka"
	},
	{
		"id": "511",
		"name": "Sindhnur",
		"state": "Karnataka"
	},
	{
		"id": "182",
		"name": "Singrauli",
		"state": "Madhya Pradesh"
	},
	{
		"id": "881",
		"name": "Sinnar",
		"state": "Maharashtra"
	},
	{
		"id": "616",
		"name": "Sira",
		"state": "Karnataka"
	},
	{
		"id": "482",
		"name": "Sircilla",
		"state": "Telangana"
	},
	{
		"id": "623",
		"name": "Sirhind Fatehgarh Sahib",
		"state": "Punjab"
	},
	{
		"id": "871",
		"name": "Sirkali",
		"state": "Tamil Nadu"
	},
	{
		"id": "798",
		"name": "Sirohi",
		"state": "Rajasthan"
	},
	{
		"id": "707",
		"name": "Sironj",
		"state": "Madhya Pradesh"
	},
	{
		"id": "208",
		"name": "Sirsa",
		"state": "Haryana"
	},
	{
		"id": "945",
		"name": "Sirsaganj",
		"state": "Uttar Pradesh"
	},
	{
		"id": "481",
		"name": "Sirsi",
		"state": "Karnataka"
	},
	{
		"id": "1146",
		"name": "Sirsi",
		"state": "Uttar Pradesh"
	},
	{
		"id": "697",
		"name": "Siruguppa",
		"state": "Karnataka"
	},
	{
		"id": "377",
		"name": "Sitamarhi",
		"state": "Bihar"
	},
	{
		"id": "222",
		"name": "Sitapur",
		"state": "Uttar Pradesh"
	},
	{
		"id": "1121",
		"name": "Sitarganj",
		"state": "Uttarakhand"
	},
	{
		"id": "734",
		"name": "Sivaganga",
		"state": "Tamil Nadu"
	},
	{
		"id": "1179",
		"name": "Sivagiri",
		"state": "Tamil Nadu"
	},
	{
		"id": "271",
		"name": "Sivakasi",
		"state": "Tamil Nadu"
	},
	{
		"id": "302",
		"name": "Siwan",
		"state": "Bihar"
	},
	{
		"id": "1109",
		"name": "Sohagpur",
		"state": "Madhya Pradesh"
	},
	{
		"id": "964",
		"name": "Sohna",
		"state": "Haryana"
	},
	{
		"id": "745",
		"name": "Sojat",
		"state": "Rajasthan"
	},
	{
		"id": "818",
		"name": "Solan",
		"state": "Himachal Pradesh"
	},
	{
		"id": "50",
		"name": "Solapur",
		"state": "Maharashtra"
	},
	{
		"id": "972",
		"name": "Sonamukhi",
		"state": "West Bengal"
	},
	{
		"id": "838",
		"name": "Sonepur",
		"state": "Bihar"
	},
	{
		"id": "1107",
		"name": "Songadh",
		"state": "Gujarat"
	},
	{
		"id": "152",
		"name": "Sonipat",
		"state": "Haryana"
	},
	{
		"id": "468",
		"name": "Sopore",
		"state": "Jammu and Kashmir"
	},
	{
		"id": "958",
		"name": "Soro",
		"state": "Odisha"
	},
	{
		"id": "987",
		"name": "Soron",
		"state": "Uttar Pradesh"
	},
	{
		"id": "1132",
		"name": "Soyagaon",
		"state": "Maharashtra"
	},
	{
		"id": "937",
		"name": "Sri Madhopur",
		"state": "Rajasthan"
	},
	{
		"id": "277",
		"name": "Srikakulam",
		"state": "Andhra Pradesh"
	},
	{
		"id": "444",
		"name": "Srikalahasti",
		"state": "Andhra Pradesh"
	},
	{
		"id": "32",
		"name": "Srinagar",
		"state": "Jammu and Kashmir"
	},
	{
		"id": "229",
		"name": "Srinagar",
		"state": "Uttarakhand"
	},
	{
		"id": "1086",
		"name": "Srinivaspur",
		"state": "Karnataka"
	},
	{
		"id": "1193",
		"name": "Srirampore",
		"state": "West Bengal"
	},
	{
		"id": "1074",
		"name": "Srisailam Project (Right Flank Colony) Township",
		"state": "Andhra Pradesh"
	},
	{
		"id": "431",
		"name": "Srivilliputhur",
		"state": "Tamil Nadu"
	},
	{
		"id": "887",
		"name": "Sugauli",
		"state": "Bihar"
	},
	{
		"id": "389",
		"name": "Sujangarh",
		"state": "Rajasthan"
	},
	{
		"id": "1133",
		"name": "Sujanpur",
		"state": "Punjab"
	},
	{
		"id": "416",
		"name": "Sullurpeta",
		"state": "Andhra Pradesh"
	},
	{
		"id": "712",
		"name": "Sultanganj",
		"state": "Bihar"
	},
	{
		"id": "332",
		"name": "Sultanpur",
		"state": "Uttar Pradesh"
	},
	{
		"id": "885",
		"name": "Sumerpur",
		"state": "Rajasthan"
	},
	{
		"id": "1036",
		"name": "Sumerpur",
		"state": "Uttar Pradesh"
	},
	{
		"id": "524",
		"name": "Sunabeda",
		"state": "Odisha"
	},
	{
		"id": "555",
		"name": "Sunam",
		"state": "Punjab"
	},
	{
		"id": "750",
		"name": "Sundargarh",
		"state": "Odisha"
	},
	{
		"id": "1052",
		"name": "Sundarnagar",
		"state": "Himachal Pradesh"
	},
	{
		"id": "571",
		"name": "Supaul",
		"state": "Bihar"
	},
	{
		"id": "947",
		"name": "Surandai",
		"state": "Tamil Nadu"
	},
	{
		"id": "692",
		"name": "Surapura",
		"state": "Karnataka"
	},
	{
		"id": "10",
		"name": "Surat",
		"state": "Gujarat"
	},
	{
		"id": "536",
		"name": "Suratgarh",
		"state": "Rajasthan"
	},
	{
		"id": "506",
		"name": "Suri",
		"state": "West Bengal"
	},
	{
		"id": "1126",
		"name": "Suriyampalayam",
		"state": "Tamil Nadu"
	},
	{
		"id": "310",
		"name": "Suryapet",
		"state": "Telangana"
	},
	{
		"id": "318",
		"name": "Tadepalligudem",
		"state": "Andhra Pradesh"
	},
	{
		"id": "306",
		"name": "Tadpatri",
		"state": "Andhra Pradesh"
	},
	{
		"id": "1198",
		"name": "Takhatgarh",
		"state": "Rajasthan"
	},
	{
		"id": "751",
		"name": "Taki",
		"state": "West Bengal"
	},
	{
		"id": "955",
		"name": "Talaja",
		"state": "Gujarat"
	},
	{
		"id": "726",
		"name": "Talcher",
		"state": "Odisha"
	},
	{
		"id": "552",
		"name": "Talegaon Dabhade",
		"state": "Maharashtra"
	},
	{
		"id": "880",
		"name": "Talikota",
		"state": "Karnataka"
	},
	{
		"id": "435",
		"name": "Taliparamba",
		"state": "Kerala"
	},
	{
		"id": "993",
		"name": "Talode",
		"state": "Maharashtra"
	},
	{
		"id": "1194",
		"name": "Talwara",
		"state": "Punjab"
	},
	{
		"id": "483",
		"name": "Tamluk",
		"state": "West Bengal"
	},
	{
		"id": "347",
		"name": "Tanda",
		"state": "Uttar Pradesh"
	},
	{
		"id": "487",
		"name": "Tandur",
		"state": "Telangana"
	},
	{
		"id": "437",
		"name": "Tanuku",
		"state": "Andhra Pradesh"
	},
	{
		"id": "896",
		"name": "Tarakeswar",
		"state": "West Bengal"
	},
	{
		"id": "1031",
		"name": "Tarana",
		"state": "Madhya Pradesh"
	},
	{
		"id": "854",
		"name": "Taranagar",
		"state": "Rajasthan"
	},
	{
		"id": "1003",
		"name": "Taraori",
		"state": "Haryana"
	},
	{
		"id": "1210",
		"name": "Tarbha",
		"state": "Odisha"
	},
	{
		"id": "792",
		"name": "Tarikere",
		"state": "Karnataka"
	},
	{
		"id": "470",
		"name": "Tarn Taran",
		"state": "Punjab"
	},
	{
		"id": "756",
		"name": "Tasgaon",
		"state": "Maharashtra"
	},
	{
		"id": "1018",
		"name": "Tehri",
		"state": "Uttarakhand"
	},
	{
		"id": "996",
		"name": "Tekkalakote",
		"state": "Karnataka"
	},
	{
		"id": "205",
		"name": "Tenali",
		"state": "Andhra Pradesh"
	},
	{
		"id": "450",
		"name": "Tenkasi",
		"state": "Tamil Nadu"
	},
	{
		"id": "1119",
		"name": "Tenu dam-cum-Kathhara",
		"state": "Jharkhand"
	},
	{
		"id": "1000",
		"name": "Terdal",
		"state": "Karnataka"
	},
	{
		"id": "529",
		"name": "Tezpur",
		"state": "Assam"
	},
	{
		"id": "685",
		"name": "Thakurdwara",
		"state": "Uttar Pradesh"
	},
	{
		"id": "1142",
		"name": "Thammampatti",
		"state": "Tamil Nadu"
	},
	{
		"id": "781",
		"name": "Thana Bhawan",
		"state": "Uttar Pradesh"
	},
	{
		"id": "16",
		"name": "Thane",
		"state": "Maharashtra"
	},
	{
		"id": "218",
		"name": "Thanesar",
		"state": "Haryana"
	},
	{
		"id": "706",
		"name": "Thangadh",
		"state": "Gujarat"
	},
	{
		"id": "154",
		"name": "Thanjavur",
		"state": "Tamil Nadu"
	},
	{
		"id": "950",
		"name": "Tharad",
		"state": "Gujarat"
	},
	{
		"id": "902",
		"name": "Tharamangalam",
		"state": "Tamil Nadu"
	},
	{
		"id": "1079",
		"name": "Tharangambadi",
		"state": "Tamil Nadu"
	},
	{
		"id": "353",
		"name": "Theni Allinagaram",
		"state": "Tamil Nadu"
	},
	{
		"id": "605",
		"name": "Thirumangalam",
		"state": "Tamil Nadu"
	},
	{
		"id": "1199",
		"name": "Thirupuvanam",
		"state": "Tamil Nadu"
	},
	{
		"id": "1042",
		"name": "Thiruthuraipoondi",
		"state": "Tamil Nadu"
	},
	{
		"id": "588",
		"name": "Thiruvalla",
		"state": "Kerala"
	},
	{
		"id": "557",
		"name": "Thiruvallur",
		"state": "Tamil Nadu"
	},
	{
		"id": "58",
		"name": "Thiruvananthapuram",
		"state": "Kerala"
	},
	{
		"id": "533",
		"name": "Thiruvarur",
		"state": "Tamil Nadu"
	},
	{
		"id": "595",
		"name": "Thodupuzha",
		"state": "Kerala"
	},
	{
		"id": "664",
		"name": "Thoubal",
		"state": "Manipur"
	},
	{
		"id": "111",
		"name": "Thrissur",
		"state": "Kerala"
	},
	{
		"id": "860",
		"name": "Thuraiyur",
		"state": "Tamil Nadu"
	},
	{
		"id": "404",
		"name": "Tikamgarh",
		"state": "Madhya Pradesh"
	},
	{
		"id": "865",
		"name": "Tilda Newra",
		"state": "Chhattisgarh"
	},
	{
		"id": "510",
		"name": "Tilhar",
		"state": "Uttar Pradesh"
	},
	{
		"id": "433",
		"name": "Tindivanam",
		"state": "Tamil Nadu"
	},
	{
		"id": "337",
		"name": "Tinsukia",
		"state": "Assam"
	},
	{
		"id": "522",
		"name": "Tiptur",
		"state": "Karnataka"
	},
	{
		"id": "1024",
		"name": "Tirora",
		"state": "Maharashtra"
	},
	{
		"id": "873",
		"name": "Tiruchendur",
		"state": "Tamil Nadu"
	},
	{
		"id": "348",
		"name": "Tiruchengode",
		"state": "Tamil Nadu"
	},
	{
		"id": "53",
		"name": "Tiruchirappalli",
		"state": "Tamil Nadu"
	},
	{
		"id": "922",
		"name": "Tirukalukundram",
		"state": "Tamil Nadu"
	},
	{
		"id": "903",
		"name": "Tirukkoyilur",
		"state": "Tamil Nadu"
	},
	{
		"id": "86",
		"name": "Tirunelveli",
		"state": "Tamil Nadu"
	},
	{
		"id": "489",
		"name": "Tirupathur",
		"state": "Tamil Nadu"
	},
	{
		"id": "1002",
		"name": "Tirupathur",
		"state": "Tamil Nadu"
	},
	{
		"id": "124",
		"name": "Tirupati",
		"state": "Andhra Pradesh"
	},
	{
		"id": "89",
		"name": "Tiruppur",
		"state": "Tamil Nadu"
	},
	{
		"id": "558",
		"name": "Tirur",
		"state": "Kerala"
	},
	{
		"id": "676",
		"name": "Tiruttani",
		"state": "Tamil Nadu"
	},
	{
		"id": "238",
		"name": "Tiruvannamalai",
		"state": "Tamil Nadu"
	},
	{
		"id": "759",
		"name": "Tiruvethipuram",
		"state": "Tamil Nadu"
	},
	{
		"id": "760",
		"name": "Tiruvuru",
		"state": "Andhra Pradesh"
	},
	{
		"id": "1050",
		"name": "Tirwaganj",
		"state": "Uttar Pradesh"
	},
	{
		"id": "890",
		"name": "Titlagarh",
		"state": "Odisha"
	},
	{
		"id": "1090",
		"name": "Tittakudi",
		"state": "Tamil Nadu"
	},
	{
		"id": "1085",
		"name": "Todabhim",
		"state": "Rajasthan"
	},
	{
		"id": "1066",
		"name": "Todaraisingh",
		"state": "Rajasthan"
	},
	{
		"id": "490",
		"name": "Tohana",
		"state": "Haryana"
	},
	{
		"id": "204",
		"name": "Tonk",
		"state": "Rajasthan"
	},
	{
		"id": "777",
		"name": "Tuensang",
		"state": "Nagaland"
	},
	{
		"id": "824",
		"name": "Tuljapur",
		"state": "Maharashtra"
	},
	{
		"id": "1040",
		"name": "Tulsipur",
		"state": "Uttar Pradesh"
	},
	{
		"id": "116",
		"name": "Tumkur",
		"state": "Karnataka"
	},
	{
		"id": "674",
		"name": "Tumsar",
		"state": "Maharashtra"
	},
	{
		"id": "612",
		"name": "Tundla",
		"state": "Uttar Pradesh"
	},
	{
		"id": "582",
		"name": "Tuni",
		"state": "Andhra Pradesh"
	},
	{
		"id": "420",
		"name": "Tura",
		"state": "Meghalaya"
	},
	{
		"id": "891",
		"name": "Uchgaon",
		"state": "Maharashtra"
	},
	{
		"id": "69",
		"name": "Udaipur",
		"state": "Rajasthan"
	},
	{
		"id": "850",
		"name": "Udaipur",
		"state": "Tripura"
	},
	{
		"id": "927",
		"name": "Udaipurwati",
		"state": "Rajasthan"
	},
	{
		"id": "317",
		"name": "Udgir",
		"state": "Maharashtra"
	},
	{
		"id": "371",
		"name": "Udhagamandalam",
		"state": "Tamil Nadu"
	},
	{
		"id": "799",
		"name": "Udhampur",
		"state": "Jammu and Kashmir"
	},
	{
		"id": "512",
		"name": "Udumalaipettai",
		"state": "Tamil Nadu"
	},
	{
		"id": "261",
		"name": "Udupi",
		"state": "Karnataka"
	},
	{
		"id": "500",
		"name": "Ujhani",
		"state": "Uttar Pradesh"
	},
	{
		"id": "76",
		"name": "Ujjain",
		"state": "Madhya Pradesh"
	},
	{
		"id": "800",
		"name": "Umarga",
		"state": "Maharashtra"
	},
	{
		"id": "845",
		"name": "Umaria",
		"state": "Madhya Pradesh"
	},
	{
		"id": "650",
		"name": "Umarkhed",
		"state": "Maharashtra"
	},
	{
		"id": "952",
		"name": "Umbergaon",
		"state": "Gujarat"
	},
	{
		"id": "573",
		"name": "Umred",
		"state": "Maharashtra"
	},
	{
		"id": "829",
		"name": "Umreth",
		"state": "Gujarat"
	},
	{
		"id": "530",
		"name": "Una",
		"state": "Gujarat"
	},
	{
		"id": "546",
		"name": "Unjha",
		"state": "Gujarat"
	},
	{
		"id": "1062",
		"name": "Unnamalaikadai",
		"state": "Tamil Nadu"
	},
	{
		"id": "188",
		"name": "Unnao",
		"state": "Uttar Pradesh"
	},
	{
		"id": "526",
		"name": "Upleta",
		"state": "Gujarat"
	},
	{
		"id": "901",
		"name": "Uran",
		"state": "Maharashtra"
	},
	{
		"id": "463",
		"name": "Uran Islampur",
		"state": "Maharashtra"
	},
	{
		"id": "796",
		"name": "Uravakonda",
		"state": "Andhra Pradesh"
	},
	{
		"id": "1069",
		"name": "Urmar Tanda",
		"state": "Punjab"
	},
	{
		"id": "803",
		"name": "Usilampatti",
		"state": "Tamil Nadu"
	},
	{
		"id": "930",
		"name": "Uthamapalayam",
		"state": "Tamil Nadu"
	},
	{
		"id": "1023",
		"name": "Uthiramerur",
		"state": "Tamil Nadu"
	},
	{
		"id": "874",
		"name": "Utraula",
		"state": "Uttar Pradesh"
	},
	{
		"id": "921",
		"name": "Vadakkuvalliyur",
		"state": "Tamil Nadu"
	},
	{
		"id": "739",
		"name": "Vadalur",
		"state": "Tamil Nadu"
	},
	{
		"id": "1011",
		"name": "Vadgaon Kasba",
		"state": "Maharashtra"
	},
	{
		"id": "982",
		"name": "Vadipatti",
		"state": "Tamil Nadu"
	},
	{
		"id": "960",
		"name": "Vadnagar",
		"state": "Gujarat"
	},
	{
		"id": "19",
		"name": "Vadodara",
		"state": "Gujarat"
	},
	{
		"id": "720",
		"name": "Vaijapur",
		"state": "Maharashtra"
	},
	{
		"id": "1077",
		"name": "Vaikom",
		"state": "Kerala"
	},
	{
		"id": "443",
		"name": "Valparai",
		"state": "Tamil Nadu"
	},
	{
		"id": "285",
		"name": "Valsad",
		"state": "Gujarat"
	},
	{
		"id": "889",
		"name": "Vandavasi",
		"state": "Tamil Nadu"
	},
	{
		"id": "351",
		"name": "Vaniyambadi",
		"state": "Tamil Nadu"
	},
	{
		"id": "207",
		"name": "Vapi",
		"state": "Gujarat"
	},
	{
		"id": "819",
		"name": "Vapi",
		"state": "Gujarat"
	},
	{
		"id": "31",
		"name": "Varanasi",
		"state": "Uttar Pradesh"
	},
	{
		"id": "727",
		"name": "Varkala",
		"state": "Kerala"
	},
	{
		"id": "30",
		"name": "Vasai-Virar",
		"state": "Maharashtra"
	},
	{
		"id": "419",
		"name": "Vatakara",
		"state": "Kerala"
	},
	{
		"id": "816",
		"name": "Vedaranyam",
		"state": "Tamil Nadu"
	},
	{
		"id": "732",
		"name": "Vellakoil",
		"state": "Tamil Nadu"
	},
	{
		"id": "181",
		"name": "Vellore",
		"state": "Tamil Nadu"
	},
	{
		"id": "599",
		"name": "Venkatagiri",
		"state": "Andhra Pradesh"
	},
	{
		"id": "220",
		"name": "Veraval",
		"state": "Gujarat"
	},
	{
		"id": "216",
		"name": "Vidisha",
		"state": "Madhya Pradesh"
	},
	{
		"id": "876",
		"name": "Vijainagar, Ajmer",
		"state": "Rajasthan"
	},
	{
		"id": "1013",
		"name": "Vijapur",
		"state": "Gujarat"
	},
	{
		"id": "807",
		"name": "Vijayapura",
		"state": "Karnataka"
	},
	{
		"id": "41",
		"name": "Vijayawada",
		"state": "Andhra Pradesh"
	},
	{
		"id": "1197",
		"name": "Vijaypur",
		"state": "Madhya Pradesh"
	},
	{
		"id": "587",
		"name": "Vikarabad",
		"state": "Telangana"
	},
	{
		"id": "652",
		"name": "Vikramasingapuram",
		"state": "Tamil Nadu"
	},
	{
		"id": "344",
		"name": "Viluppuram",
		"state": "Tamil Nadu"
	},
	{
		"id": "521",
		"name": "Vinukonda",
		"state": "Andhra Pradesh"
	},
	{
		"id": "561",
		"name": "Viramgam",
		"state": "Gujarat"
	},
	{
		"id": "428",
		"name": "Virudhachalam",
		"state": "Tamil Nadu"
	},
	{
		"id": "439",
		"name": "Virudhunagar",
		"state": "Tamil Nadu"
	},
	{
		"id": "18",
		"name": "Visakhapatnam",
		"state": "Andhra Pradesh"
	},
	{
		"id": "494",
		"name": "Visnagar",
		"state": "Gujarat"
	},
	{
		"id": "1014",
		"name": "Viswanatham",
		"state": "Tamil Nadu"
	},
	{
		"id": "641",
		"name": "Vita",
		"state": "Maharashtra"
	},
	{
		"id": "149",
		"name": "Vizianagaram",
		"state": "Andhra Pradesh"
	},
	{
		"id": "495",
		"name": "Vrindavan",
		"state": "Uttar Pradesh"
	},
	{
		"id": "737",
		"name": "Vyara",
		"state": "Gujarat"
	},
	{
		"id": "725",
		"name": "Wadgaon Road",
		"state": "Maharashtra"
	},
	{
		"id": "417",
		"name": "Wadhwan",
		"state": "Gujarat"
	},
	{
		"id": "754",
		"name": "Wadi",
		"state": "Karnataka"
	},
	{
		"id": "791",
		"name": "Wai",
		"state": "Maharashtra"
	},
	{
		"id": "514",
		"name": "Wanaparthy",
		"state": "Telangana"
	},
	{
		"id": "525",
		"name": "Wani",
		"state": "Maharashtra"
	},
	{
		"id": "689",
		"name": "Wankaner",
		"state": "Gujarat"
	},
	{
		"id": "1027",
		"name": "Wara Seoni",
		"state": "Madhya Pradesh"
	},
	{
		"id": "56",
		"name": "Warangal",
		"state": "Telangana"
	},
	{
		"id": "311",
		"name": "Wardha",
		"state": "Maharashtra"
	},
	{
		"id": "1067",
		"name": "Warhapur",
		"state": "Uttar Pradesh"
	},
	{
		"id": "821",
		"name": "Warisaliganj",
		"state": "Bihar"
	},
	{
		"id": "658",
		"name": "Warora",
		"state": "Maharashtra"
	},
	{
		"id": "1213",
		"name": "Warud",
		"state": "Maharashtra"
	},
	{
		"id": "410",
		"name": "Washim",
		"state": "Maharashtra"
	},
	{
		"id": "805",
		"name": "Wokha",
		"state": "Nagaland"
	},
	{
		"id": "423",
		"name": "Yadgir",
		"state": "Karnataka"
	},
	{
		"id": "159",
		"name": "Yamunanagar",
		"state": "Haryana"
	},
	{
		"id": "563",
		"name": "Yanam",
		"state": "Puducherry"
	},
	{
		"id": "279",
		"name": "Yavatmal",
		"state": "Maharashtra"
	},
	{
		"id": "780",
		"name": "Yawal",
		"state": "Maharashtra"
	},
	{
		"id": "833",
		"name": "Yellandu",
		"state": "Telangana"
	},
	{
		"id": "350",
		"name": "Yemmiganur",
		"state": "Andhra Pradesh"
	},
	{
		"id": "855",
		"name": "Yerraguntla",
		"state": "Andhra Pradesh"
	},
	{
		"id": "622",
		"name": "Yevla",
		"state": "Maharashtra"
	},
	{
		"id": "813",
		"name": "Zaidpur",
		"state": "Uttar Pradesh"
	},
	{
		"id": "840",
		"name": "Zamania",
		"state": "Uttar Pradesh"
	},
	{
		"id": "779",
		"name": "Zira",
		"state": "Punjab"
	},
	{
		"id": "346",
		"name": "Zirakpur",
		"state": "Punjab"
	},
	{
		"id": "765",
		"name": "Zunheboto",
		"state": "Nagaland"
	}
];

export default cities;
