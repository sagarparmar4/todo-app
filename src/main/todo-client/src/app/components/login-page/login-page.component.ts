import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { SecurityService } from 'src/app/services/security/security.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent implements OnInit {

  @ViewChild('signinForm', { static: false })
  signinForm: NgForm;

  username: string = null;
  password: string = null;

  constructor(private securityService: SecurityService) { }

  ngOnInit() {
  }

  /**
   * Validate user details and rediect user to dashboard
   */
  validateUser() {
    this.securityService.updateCredentials(this.signinForm.value.username, this.signinForm.value.password);
    this.securityService.validateUser();
  }

}
