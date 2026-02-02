import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TurbineListComponent } from './turbine-list.component';

describe('TurbineListComponent', () => {
  let component: TurbineListComponent;
  let fixture: ComponentFixture<TurbineListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TurbineListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TurbineListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
