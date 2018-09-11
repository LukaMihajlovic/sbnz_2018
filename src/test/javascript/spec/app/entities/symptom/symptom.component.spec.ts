/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SbnzTestModule } from '../../../test.module';
import { SymptomComponent } from '../../../../../../main/webapp/app/entities/symptom/symptom.component';
import { SymptomService } from '../../../../../../main/webapp/app/entities/symptom/symptom.service';
import { Symptom } from '../../../../../../main/webapp/app/entities/symptom/symptom.model';

describe('Component Tests', () => {

    describe('Symptom Management Component', () => {
        let comp: SymptomComponent;
        let fixture: ComponentFixture<SymptomComponent>;
        let service: SymptomService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SbnzTestModule],
                declarations: [SymptomComponent],
                providers: [
                    SymptomService
                ]
            })
            .overrideTemplate(SymptomComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SymptomComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SymptomService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Symptom(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.symptoms[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
