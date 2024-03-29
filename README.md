<a id="readme-top"></a>
<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->

[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]
<div align="center">
<h3 align="center">All You Need Is Diet Plan</h3>
  <p align="center">
    Web app to generate personalized weekly Diet Plan
    <br />
    <a href="https://github.com/karolina-wisniewska/All-You-Need-Is-Diet-Plan"><strong>Explore the docs »</strong></a>
    <br />
  </p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#demo">Demo</a></li>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
  </ol>
</details>


<a name="about-the-project"></a>
<!-- ABOUT THE PROJECT -->
## About The Project

Everyone wishes to achieve their dream weight, but many people have no idea where to start.

There are two roles in the application:
1. USER:

User can track weight changes and get personalized weekly diet plan based on:
* calculated daily calories, 
* culinary preferences, 
* diet type and health issues/allergies. 

User can also make a video call to dietitian (ADMIN).

2. ADMIN:

Admin can accept calls from users.

The app uses:
* Highcharts API to show weight history 
* Edamam API to provide recipes with required criteria
* Kurento WebRTC Media Server to enable video call

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<a id="built-with"></a>
### Built With
* Java 17
* MySQL
* Maven
* Spring Boot
* Spring Security
* Hibernate
* Lombok
* Thymeleaf
* Bootstrap (UI Presentation)
* HighCharts
* Edamam
* Kurento Media Server
* IntelliJ IDEA
<p align="right">(<a href="#readme-top">back to top</a>)</p>

<a id="getting-started"></a>
<!-- GETTING STARTED -->
## Getting Started

<a id="demo"></a>
### Demo
1. Check out App Demo on YouTube:
   <br />
   <a href="https://www.youtube.com/watch?v=erH0mp8U8io"><strong>Watch App Demo »</strong></a>
   <br />

2. Check out Video Call Demo on YouTube:
   <br />
   <a href="https://www.youtube.com/watch?v=5qotQKvWcBk"><strong>Watch Video Call Demo »</strong></a>
   <br />

<a id="prerequisities"></a>
### Prerequisities
Docker

<a id="installation"></a>
### Installation

1. To launch the application, you need to clone the GitHub project:
   ```sh
   git clone https://github.com/karolina-wisniewska/All-You-Need-Is-Diet-Plan.git
   ```
   
2. Change directory to project root directory:  
   ```sh
   cd All-You-Need-Is-Diet-Plan/
   ```
   
3. Run docker-compose up in detached mode:
   ```sh
   docker-compose up -d
   ```

4. The web application starts on port 9090 in the localhost by default. Therefore, open the URL https://localhost:9090/ in a WebRTC compliant browser (Chrome, Firefox).


5. To close the app, remove containers and volumes run:
   ```sh
   docker-compose down -v
   ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<a id="contributing"></a>
<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<a id="license"></a>
<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[license-shield]: https://img.shields.io/github/license/othneildrew/Best-README-Template.svg?style=for-the-badge
[license-url]: https://github.com/othneildrew/Best-README-Template/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/karolina-wi

