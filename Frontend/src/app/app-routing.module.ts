import {NgModule} from "@angular/core"
import {RouterModule, Routes} from "@angular/router"
import {LoginComponent} from "./login/login.component"
import {MyProfileComponent} from "./content/Agent/traveller-reginster/my-profile.component"
import {RegisterComponent} from "./register/register.component"
import {FuelAvailabilityComponent} from "./content/Backoffice/Trains/fuel-availability.component"
import {OrderComponent} from "./content/Backoffice/userprofile/order.component"
import {FuelConsumptionCustomerComponent} from "./content/Agent/manage-user/fuel-consumption-customer.component";
import {
  UpdateTravellerProfileComponent
} from "./content/Agent/update-traveller-profile/update-traveller-profile.component";
import {HistoryComponent} from "./content/Agent/history/history.component";


const routes: Routes = [
  {
    path: "",
    redirectTo: "login",
    pathMatch: "full"
  },
  {
    path: "login",
    component: LoginComponent
  },
  {
    path: "register",
    component: RegisterComponent
  },
  {
    path: "manage-trains",
    component: FuelAvailabilityComponent
  },
  { //customer
    path: "my_profile",
    component: MyProfileComponent
  },
  {
    path: 'manage_user',
    component: FuelConsumptionCustomerComponent,
  },
  {
    path: 'history',
    component: HistoryComponent,
  },
  {
    path: 'update_traveller',
    component: UpdateTravellerProfileComponent,
  },

  {
    path: "manage-status",
    component: OrderComponent
  },


]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
