import http from 'k6/http';
import { check } from 'k6';

export const options = {
  stages: [
    { duration: '10s', target: 50 },   // naik ke 50 VU
    { duration: '50s', target: 100 },  // naik ke 100 VU
    { duration: '10s', target: 0 },    // turun ke 0
  ],
  thresholds: {
    http_req_duration: ['p(95)<200'], // 95th percentile < 200ms
  },
};

export default function () {
  const res = http.get('http://localhost:8080/api/categories');

  check(res, {
    'status is 200': (r) => r.status === 200,
  });
}