/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SbnzTestModule } from '../../../test.module';
import { SymptomDetailComponent } from '../../../../../../main/webapp/app/entities/symptom/symptom-detail.component';
import { SymptomService } from '../../../../../../main/webapp/app/entities/symptom/symptom.service';
import { Symptom } from '../../../../../../main/webapp/app/entities/symptom/symptom.model';

describe('Component Tests', () => {

    describe('Symptom Management Detail Component', () => {
        let comp: SymptomDetailComponent;
        let fixture: ComponentFixture<SymptomDetailComponent>;
        let service: SymptomService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SbnzTestModule],
                declarations: [SymptomDetailComponent],
                providers: [
                    SymptomService
                ]
            })
            .overrideTemplate(SymptomDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SymptomDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SymptomService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Symptom(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.symptom).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
