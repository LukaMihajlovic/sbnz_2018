/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SbnzTestModule } from '../../../test.module';
import { UiawqDetailComponent } from '../../../../../../main/webapp/app/entities/uiawq/uiawq-detail.component';
import { UiawqService } from '../../../../../../main/webapp/app/entities/uiawq/uiawq.service';
import { Uiawq } from '../../../../../../main/webapp/app/entities/uiawq/uiawq.model';

describe('Component Tests', () => {

    describe('Uiawq Management Detail Component', () => {
        let comp: UiawqDetailComponent;
        let fixture: ComponentFixture<UiawqDetailComponent>;
        let service: UiawqService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SbnzTestModule],
                declarations: [UiawqDetailComponent],
                providers: [
                    UiawqService
                ]
            })
            .overrideTemplate(UiawqDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UiawqDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UiawqService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Uiawq(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.uiawq).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
