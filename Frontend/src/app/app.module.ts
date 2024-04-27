import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {NavbarComponent} from './navbar/navbar.component';
import {FooterComponent} from './footer/footer.component';
import {HeaderComponent} from './header/header.component';
import {ContentComponent} from './content/content.component';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {RegisterCustomerComponent} from './register/register-Agent/register-customer.component';
import {RegisterStationComponent} from './register/register-Officer/register-station.component';
import {MyProfileComponent} from './content/Agent/traveller-reginster/my-profile.component';
import {FuelAvailabilityComponent} from './content/Backoffice/Trains/fuel-availability.component';
import {ZXingScannerModule} from "@zxing/ngx-scanner";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import {DatePipe} from "@angular/common";
import {OrderComponent} from './content/Backoffice/userprofile/order.component';
import {ScrollingModule} from "@angular/cdk/scrolling";
import {NgApexchartsModule} from "ng-apexcharts";
import {FuelConsumptionCustomerComponent} from './content/Agent/manage-user/fuel-consumption-customer.component';
import {NotifierModule, NotifierOptions} from "angular-notifier";
import { AlertBoxComponent } from './alert-box/alert-box.component';
import { EmailValidatorDirective } from './_validator/email-validator.directive';
import { NicValidatorDirective } from './_validator/nic-validator.directive';
import { TelephoneValidatorDirective } from './_validator/telephone-validator.directive';
import { VehicleNumberValidatorDirective } from './_validator/vehicle-number-validator.directive';
import { LoginValidatorDirective } from './_validator/login-validator.directive';
import { UpdateTravellerProfileComponent } from './content/Agent/update-traveller-profile/update-traveller-profile.component';
import { HistoryComponent } from './content/Agent/history/history.component';


const customNotifierOptions: NotifierOptions = {
  position: {
    horizontal: {
      position: "middle",
      distance: 5
    },
    vertical: {
      position: "top",
      distance: 10,
      gap: 10
    }
  },
  theme: "material",
  behaviour: {
    autoHide: 5000,
    onClick: false,
    onMouseover: "pauseAutoHide",
    showDismissButton: false,
    stacking: 4
  },
  animations: {
    enabled: true,
    show: {
      preset: "slide",
      speed: 300,
      easing: "ease"
    },
    hide: {
      preset: "fade",
      speed: 300,
      easing: "ease",
      offset: 50
    },
    shift: {
      speed: 300,
      easing: "ease"
    },
    overlap: 150
  }
};

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    FooterComponent,
    HeaderComponent,
    ContentComponent,
    LoginComponent,
    RegisterComponent,
    RegisterCustomerComponent,
    RegisterStationComponent,
    MyProfileComponent,
    FuelAvailabilityComponent,
    OrderComponent,
    FuelConsumptionCustomerComponent,
    AlertBoxComponent,
    EmailValidatorDirective,
    NicValidatorDirective,
    TelephoneValidatorDirective,
    VehicleNumberValidatorDirective,
    LoginValidatorDirective,
    UpdateTravellerProfileComponent,
    HistoryComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ZXingScannerModule,
    HttpClientModule,
    FormsModule,
    ScrollingModule,
    NgApexchartsModule,
    NotifierModule.withConfig(customNotifierOptions)
  ],
  providers: [DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule {
}
