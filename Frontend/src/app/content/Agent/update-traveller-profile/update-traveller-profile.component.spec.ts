import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateTravellerProfileComponent } from './update-traveller-profile.component';

describe('UpdateTravellerProfileComponent', () => {
  let component: UpdateTravellerProfileComponent;
  let fixture: ComponentFixture<UpdateTravellerProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateTravellerProfileComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateTravellerProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
