-- User
INSERT INTO users (email, password, profile_image, nickname, role, provider_type)
VALUES ('test@test.com', '$2a$10$Ce1gYtGZXnW7u1fP35m5zefMI95YB3CoJMiGowrtOmInTJ10y1LNO',
        'https://bannabe-test-bucket.s3.ap-northeast-2.amazonaws.com/profile-images/anonymous.png', 'TestUser', 'USER', 'LOCAL');

-- RentalStation
INSERT INTO rental_stations (name, address, latitude, longitude, open_time, close_time, close_day, status, grade)
VALUES ('강남역점', '서울특별시 강남구 강남대로 396', 37.498095, 127.027610, '10:00', '21:00', '없음', 'OPEN', 'FIRST'),
       ('홍대입구역점', '서울특별시 마포구 양화로 160', 37.557192, 126.924618, '10:00', '21:00', '없음', 'OPEN', 'FIRST'),
       ('커피빈 강남역먹자골목점', '서울특별시 강남구 강남대로 414', 37.499723, 127.027428, '07:00', '22:00', '없음', 'OPEN', 'FIRST'),
       ('스타벅스 강남GT타워점', '서울특별시 강남구 테헤란로 134', 37.498688, 127.026013, '07:00', '22:00', '없음', 'OPEN', 'FIRST');

-- RentalItemType
INSERT INTO rental_item_types (id, category, name, image, description, price)
VALUES (1, 'CHARGER', '노트북 고출력 충전기', 'example.png', '100W PD 고속 충전기', 2000),
       (2, 'CHARGER', '노트북 PD 충전기', 'charger-2.png', '65W PD 충전기', 1500),
       (3, 'CHARGER', 'C타입 충전기', 'charger-3.png', '25W 고속 충전기', 1000),
       (4, 'CHARGER', '8핀 충전기', 'charger-4.png', '20W 고속 충전기', 1000),

       (5, 'CABLE', 'HDMI 케이블', 'cable-1.png', '4K 지원 HDMI 2.0', 1000),
       (6, 'CABLE', 'DP 케이블', 'cable-2.png', '4K 지원 DisplayPort 1.4', 1000),
       (7, 'CABLE', 'C to C 케이블', 'cable-3.png', '100W PD 지원', 500),
       (8, 'CABLE', 'C to A 케이블', 'cable-4.png', '고속 데이터 전송', 500),

       (9, 'HUB', 'SD 카드 독 (Type-C)', 'dock-1.png', 'SD/MicroSD 지원', 1500),
       (10, 'HUB', 'USB 독 (Type-C)', 'dock-2.png', 'USB 3.0 4포트', 1500),
       (11, 'HUB', '멀티 독 (Type-C)', 'dock-3.png', 'HDMI, USB, SD 카드 지원', 2000),

       (12, 'POWER_BANK', '노트북용 보조배터리', 'powerbank-1.png', '30000mAh, 100W PD', 3000),
       (13, 'POWER_BANK', '휴대폰용 보조배터리', 'powerbank-2.png', '10000mAh, 25W', 1500),
       (14, 'ETC', '발표용 리모콘', 'etc-1.png', '레이저 포인터 내장', 1000);

-- RentalItem
INSERT INTO rental_items (status, token, rental_item_type_id, current_station_id)
VALUES ('AVAILABLE', 'kCQzZkTC20', 1, 1),
       ('AVAILABLE', 'PsT51cpJ8t', 1, 4),
       ('AVAILABLE', 'Z2ZUjXO3tG', 1, 3),
       ('AVAILABLE', 'lcuSauqWri', 1, 1),
       ('AVAILABLE', 'p9Tq1UovdT', 1, 2),
       ('AVAILABLE', '451nYXs2aq', 1, 1),
       ('AVAILABLE', 'cQuMzqJwYa', 1, 4),
       ('AVAILABLE', 'NAt5iQv03a', 1, 1),
       ('AVAILABLE', 'QMwQAOl9HE', 1, 2),
       ('AVAILABLE', 'wYwoNTLmlG', 1, 4),
       ('AVAILABLE', 'Y45PftSqvF', 1, 1),
       ('AVAILABLE', 'lefbpYaqQf', 1, 3),
       ('AVAILABLE', 'FjXeKC6hzV', 1, 3),
       ('AVAILABLE', 'p0DvYzVTqh', 1, 2),
       ('AVAILABLE', 'NMTWrxKFR7', 1, 4),
       ('AVAILABLE', 'RXVNYqMMTt', 1, 1),
       ('AVAILABLE', 'PowZxOG87S', 1, 2),
       ('AVAILABLE', 'd4SE67iFkl', 1, 2),
       ('AVAILABLE', 'u5ugVVtXn3', 1, 2),
       ('AVAILABLE', 'wrRrcYBpcx', 1, 1),
       ('AVAILABLE', '5HHBrGaFjt', 1, 1),
       ('AVAILABLE', 'oUGPXrKI5Y', 1, 1),
       ('AVAILABLE', 'pRpD68Ctvh', 1, 3),
       ('AVAILABLE', 'v0ixn8GV8g', 1, 2),
       ('AVAILABLE', 'TMeLFEcuro', 1, 4),
       ('AVAILABLE', 'cQe1oMebLS', 1, 4),
       ('AVAILABLE', 'ckCYn4w38l', 1, 3),
       ('AVAILABLE', 'ZrNBLtpqFA', 1, 1),
       ('AVAILABLE', 'hxbd9X70vX', 1, 2),
       ('AVAILABLE', 'mYKJdUjYQB', 1, 3),
       ('AVAILABLE', '1KN17pyFIn', 1, 1),
       ('AVAILABLE', 'pPy6IpcoiV', 1, 1),
       ('AVAILABLE', 'uU7ukCOkrG', 1, 3),
       ('AVAILABLE', '4iNU6zffIo', 1, 4),
       ('AVAILABLE', 'S4v1y4aNeY', 1, 1),
       ('AVAILABLE', '2vpZ0L1WLX', 1, 2),
       ('AVAILABLE', 'e1AQsUxQvn', 1, 1),
       ('AVAILABLE', 'ugvzy4pwOu', 2, 1),
       ('AVAILABLE', 'IjABGwO7k1', 2, 3),
       ('AVAILABLE', 'mCRf9IzkSB', 2, 1),
       ('AVAILABLE', 'YwHVAm9JUq', 2, 4),
       ('AVAILABLE', 'GaZsc3EPPs', 2, 3),
       ('AVAILABLE', 'W1IjmB3oCf', 2, 2),
       ('AVAILABLE', '3B8sv2zXFu', 2, 2),
       ('AVAILABLE', 'ZHIdFR9LPp', 2, 2),
       ('AVAILABLE', 'EYpOno4AEC', 2, 2),
       ('AVAILABLE', 'eHhNOIxid8', 2, 3),
       ('AVAILABLE', 'tZjcDOJmeI', 2, 2),
       ('AVAILABLE', 'WX7zqNAXis', 2, 4),
       ('AVAILABLE', '4xHbF3gjbJ', 2, 4),
       ('AVAILABLE', 'UvKrSdWltv', 2, 4),
       ('AVAILABLE', 'qVPprRdNIJ', 2, 1),
       ('AVAILABLE', 'rw5oiRxHs1', 2, 2),
       ('AVAILABLE', 'Msp9ZL4rcL', 2, 1),
       ('AVAILABLE', 'xxa75Gtwx6', 2, 3),
       ('AVAILABLE', 'CKYFUjpvJB', 2, 3),
       ('AVAILABLE', 'cH8s8VBnxs', 2, 1),
       ('AVAILABLE', 'OdSLFIE0s0', 2, 3),
       ('AVAILABLE', 'Gd27dR85mD', 2, 3),
       ('AVAILABLE', 'wsmR1AsaNR', 2, 2),
       ('AVAILABLE', 'FWiduaOI7Y', 2, 1),
       ('AVAILABLE', '9QjM4Hy3Yd', 2, 4),
       ('AVAILABLE', '9uLp8O1vyh', 2, 2),
       ('AVAILABLE', '3VsQP1Fy1p', 2, 3),
       ('AVAILABLE', '42Ol5rvMlp', 2, 2),
       ('AVAILABLE', 'snDFoLFQkl', 2, 4),
       ('AVAILABLE', 'Aq05b357QD', 2, 3),
       ('AVAILABLE', 'Y7rTBsZx3z', 2, 2),
       ('AVAILABLE', 'E2uIn48JAz', 2, 1),
       ('AVAILABLE', 'tnByMgOHXx', 2, 4),
       ('AVAILABLE', '3zGooPZ1Bw', 2, 2),
       ('AVAILABLE', '1lsDwAzcNO', 2, 4),
       ('AVAILABLE', 'XfkZQPKl7O', 2, 4),
       ('AVAILABLE', '9mkwCSMCnl', 2, 4),
       ('AVAILABLE', 'HY1C1j804N', 2, 1),
       ('AVAILABLE', 'StjAxbjk7X', 2, 3),
       ('AVAILABLE', 'bnibl4Qmnw', 2, 1),
       ('AVAILABLE', 'QoNWmrmHGe', 2, 1),
       ('AVAILABLE', '4TvpOSueio', 2, 4),
       ('AVAILABLE', 'EJ5dA0aPt1', 2, 4),
       ('AVAILABLE', '5zQb0C2cDs', 2, 1),
       ('AVAILABLE', 'DlzC904BT7', 2, 4),
       ('AVAILABLE', 'rPJ3klTfpe', 2, 3),
       ('AVAILABLE', 'afBcn4o4tA', 2, 3),
       ('AVAILABLE', '9WZ1eWS6PR', 2, 1),
       ('AVAILABLE', '9yH7JHCsQ6', 2, 1),
       ('AVAILABLE', 'q6UgYEnjYR', 3, 1),
       ('AVAILABLE', '09hkLHi7C6', 3, 4),
       ('AVAILABLE', 'pWCxZ3OWH7', 3, 4),
       ('AVAILABLE', 'lVgyMfI27t', 3, 4),
       ('AVAILABLE', 'f219WxfENd', 3, 1),
       ('AVAILABLE', 'gkPL91MsJ1', 3, 2),
       ('AVAILABLE', 'O7ZEtynEnR', 3, 2),
       ('AVAILABLE', 'dhKrzEPM6K', 3, 3),
       ('AVAILABLE', 'YUira9BNZh', 3, 3),
       ('AVAILABLE', 'gCqLFStrkZ', 3, 3),
       ('AVAILABLE', '5VMrK6vtsm', 3, 1),
       ('AVAILABLE', 'zqmi14HevS', 3, 2),
       ('AVAILABLE', 'DjK5L9kkiN', 3, 4),
       ('AVAILABLE', 'mc1ziURLg4', 3, 1),
       ('AVAILABLE', 'sxMGpTFExs', 3, 1),
       ('AVAILABLE', 'SISKzxlvwr', 3, 3),
       ('AVAILABLE', 'pW8j3hRjAG', 3, 3),
       ('AVAILABLE', 'bdFLZJWrMu', 3, 4),
       ('AVAILABLE', 'q8MePGfiez', 3, 4),
       ('AVAILABLE', 'pawiOr2nFL', 3, 4),
       ('AVAILABLE', '43Itd7SdWJ', 3, 1),
       ('AVAILABLE', 'LB71LPuIr4', 3, 4),
       ('AVAILABLE', '9U3YHEfPX4', 3, 1),
       ('AVAILABLE', 'hvQThlCwQt', 3, 3),
       ('AVAILABLE', 'Kc7xy0Avrm', 3, 3),
       ('AVAILABLE', 'M0sLU41k84', 3, 1),
       ('AVAILABLE', 'mRZtnWF3qq', 3, 2),
       ('AVAILABLE', '6YzdtWjtjK', 4, 4),
       ('AVAILABLE', 'L6S1PvmVLw', 4, 2),
       ('AVAILABLE', 'uKHA7Hf2is', 4, 4),
       ('AVAILABLE', 'EniWUx0VtM', 4, 3),
       ('AVAILABLE', 'vPnMwqf9uq', 4, 4),
       ('AVAILABLE', 'a4ewUa13tI', 4, 4),
       ('AVAILABLE', 'Zj8OhF5RDJ', 4, 2),
       ('AVAILABLE', 'aBvpIX1XKs', 4, 3),
       ('AVAILABLE', 'IPQ58qKurJ', 4, 1),
       ('AVAILABLE', 'h1tsmv59Pp', 4, 1),
       ('AVAILABLE', 'ShCL50AlKv', 4, 2),
       ('AVAILABLE', 'Gmhr4XfHdJ', 4, 3),
       ('AVAILABLE', 'Yu5paRVuPJ', 4, 2),
       ('AVAILABLE', 'DoTzJA9g04', 4, 1),
       ('AVAILABLE', 'Lcsr9Qy4nW', 4, 3),
       ('AVAILABLE', 'KHK9jEGCHA', 4, 2),
       ('AVAILABLE', 'z6LtxFSYxq', 4, 2),
       ('AVAILABLE', 'YdTjmUF5e3', 4, 4),
       ('AVAILABLE', '6lBtGDq8Zr', 4, 2),
       ('AVAILABLE', 'vPQAZpFf3Q', 4, 3),
       ('AVAILABLE', '4IYSHYqRkz', 4, 2),
       ('AVAILABLE', 'gTdVbN0u3H', 4, 4),
       ('AVAILABLE', 'uQq9fjherN', 4, 2),
       ('AVAILABLE', 'T6GbHu4ozW', 4, 1),
       ('AVAILABLE', 'LsGjQMlEJt', 4, 1),
       ('AVAILABLE', 'iQVGBNpLot', 4, 1),
       ('AVAILABLE', '54lTQGciFk', 4, 1),
       ('AVAILABLE', 'Vp98qYpyef', 4, 1),
       ('AVAILABLE', 'pJLccbB4fr', 4, 4),
       ('AVAILABLE', 'hjk0y6U8Fb', 4, 2),
       ('AVAILABLE', 'NewlNNN1IN', 4, 3),
       ('AVAILABLE', '0OsR2a9MpC', 4, 4),
       ('AVAILABLE', 'YTMg9APYUX', 4, 4),
       ('AVAILABLE', 'L9ObbpvvKb', 4, 3),
       ('AVAILABLE', 'C0Eu6YBQs1', 4, 2),
       ('AVAILABLE', 'zRtEyXXyll', 4, 2),
       ('AVAILABLE', 'IedyrN6Ncu', 5, 4),
       ('AVAILABLE', 'dlX9ahOrBV', 5, 4),
       ('AVAILABLE', 'EMudt5OB8n', 5, 1),
       ('AVAILABLE', 'UAi2gHTAFa', 5, 4),
       ('AVAILABLE', 'uqGQ0wgB0h', 5, 4),
       ('AVAILABLE', 'a2aR3VPkC9', 5, 4),
       ('AVAILABLE', 'gbrE3C3Xd3', 5, 2),
       ('AVAILABLE', '9K9mDks5Cy', 5, 4),
       ('AVAILABLE', 'B6BoBNSd3w', 5, 4),
       ('AVAILABLE', 'r6NI812tLt', 5, 1),
       ('AVAILABLE', 'Zryoq2op8b', 5, 1),
       ('AVAILABLE', '0RI6i7OZtE', 5, 3),
       ('AVAILABLE', 'G6utkAOoPo', 5, 2),
       ('AVAILABLE', 'SXSt8Go9KR', 5, 1),
       ('AVAILABLE', 'esBEM5k85J', 5, 1),
       ('AVAILABLE', 'h67Xs72uHG', 5, 3),
       ('AVAILABLE', 'hHXC10emdr', 5, 3),
       ('AVAILABLE', '7ayH72YiTw', 5, 3),
       ('AVAILABLE', 'vPvlU9PNFH', 5, 2),
       ('AVAILABLE', 'MZ7Bb0YKca', 5, 1),
       ('AVAILABLE', 'HX9ymrHhwg', 5, 3),
       ('AVAILABLE', '1nDxiV2QAd', 6, 1),
       ('AVAILABLE', 'DfOD8mV2Rh', 6, 4),
       ('AVAILABLE', 'fY0seW2V2e', 6, 1),
       ('AVAILABLE', 'JC1Jt6y5KQ', 6, 2),
       ('AVAILABLE', 'VVurL49wdt', 6, 2),
       ('AVAILABLE', 'RH3gTnAYhU', 6, 1),
       ('AVAILABLE', 'E0GHAn4t3E', 6, 1),
       ('AVAILABLE', 'VwJD7HyylZ', 6, 2),
       ('AVAILABLE', 'elwtrUXC3Y', 6, 2),
       ('AVAILABLE', 'MIP4MjnUV7', 6, 1),
       ('AVAILABLE', 'kwqOUyN89K', 6, 2),
       ('AVAILABLE', '49N93zCRep', 6, 1),
       ('AVAILABLE', 'V1ercJW85d', 6, 2),
       ('AVAILABLE', '4u3XH4FPuL', 6, 3),
       ('AVAILABLE', 'Nn6zva5dC7', 6, 1),
       ('AVAILABLE', 'eibkuv94bN', 6, 2),
       ('AVAILABLE', 'I9KL0JIqvG', 6, 3),
       ('AVAILABLE', 'blCPJ4UX2J', 6, 1),
       ('AVAILABLE', '2h5J4nRtzX', 6, 2),
       ('AVAILABLE', 'CLMjwknrHh', 6, 2),
       ('AVAILABLE', 'jegCQMyv6o', 6, 1),
       ('AVAILABLE', 'MGx4UNCnWv', 6, 4),
       ('AVAILABLE', 'LeiyD2NBpN', 6, 2),
       ('AVAILABLE', 'rK6Wgj5QVK', 6, 3),
       ('AVAILABLE', 'EJnXiTGMZn', 6, 2),
       ('AVAILABLE', 'eC9gPQ5p2t', 7, 3),
       ('AVAILABLE', 'JIw7jWIgL2', 7, 1),
       ('AVAILABLE', 'HpmteOrEan', 7, 1),
       ('AVAILABLE', 'YOKEXxrnEu', 7, 4),
       ('AVAILABLE', 'bbYXeBPbaG', 7, 2),
       ('AVAILABLE', '6Qc3krJLSp', 7, 2),
       ('AVAILABLE', 'YTcPGRo1Zy', 7, 3),
       ('AVAILABLE', 'Nptb2ieiho', 7, 3),
       ('AVAILABLE', '3vuz1xGjEv', 7, 4),
       ('AVAILABLE', 'KjiBatgJ8f', 7, 2),
       ('AVAILABLE', 'y634uxA49o', 7, 4),
       ('AVAILABLE', 'D74Gc95C5x', 7, 4),
       ('AVAILABLE', 'yMvE6VEXF7', 7, 4),
       ('AVAILABLE', 'hAJQuC8mPA', 7, 4),
       ('AVAILABLE', 'XgVZwiPxmD', 7, 1),
       ('AVAILABLE', '4eL06rFC8e', 7, 3),
       ('AVAILABLE', '8kWRYFzYRQ', 7, 2),
       ('AVAILABLE', 'SPDgyxt8t1', 7, 4),
       ('AVAILABLE', 'YR1Tf7t0UF', 7, 4),
       ('AVAILABLE', '4JhIiEmV72', 7, 4),
       ('AVAILABLE', 'OcgTeOnIbu', 7, 2),
       ('AVAILABLE', '7QZ2F8x2VD', 7, 1),
       ('AVAILABLE', 'NXYzaQHWin', 7, 3),
       ('AVAILABLE', 'A6IafECH5U', 7, 3),
       ('AVAILABLE', 'TZLsnGTISP', 8, 1),
       ('AVAILABLE', 'hAOpATuysl', 8, 4),
       ('AVAILABLE', 'ileCOJHdTu', 8, 3),
       ('AVAILABLE', '2RsZtZmeqR', 8, 1),
       ('AVAILABLE', 'ZiZ1DFAPIc', 8, 3),
       ('AVAILABLE', 'XEBEbXtaXV', 8, 3),
       ('AVAILABLE', 'u5Hf56mQCX', 8, 4),
       ('AVAILABLE', 'Jvyy3HYSZa', 8, 1),
       ('AVAILABLE', 'vDmcgZ64Ls', 8, 2),
       ('AVAILABLE', 'QwvLKjWZQZ', 8, 3),
       ('AVAILABLE', 'elouXiwoig', 8, 1),
       ('AVAILABLE', 'ZoMqSbN9CU', 8, 2),
       ('AVAILABLE', 'TGXbPyoqa3', 8, 3),
       ('AVAILABLE', 'vJfxkV5MWu', 8, 4),
       ('AVAILABLE', 'cWLBvtBT2g', 8, 2),
       ('AVAILABLE', 'sH3ISUkBPQ', 8, 1),
       ('AVAILABLE', '90aSgVFrWd', 8, 1),
       ('AVAILABLE', '0QEU2Exz1L', 8, 1),
       ('AVAILABLE', 'theEhjZ6dW', 8, 1),
       ('AVAILABLE', 'YeNQNBpf1Y', 8, 3),
       ('AVAILABLE', 'NJykS5kX2o', 8, 4),
       ('AVAILABLE', 'wT2VTnCYFw', 8, 3),
       ('AVAILABLE', '8byMmaZTTd', 8, 3),
       ('AVAILABLE', 'CQPqUW5HPe', 9, 2),
       ('AVAILABLE', 'h3jyK5Uh13', 9, 2),
       ('AVAILABLE', 'L17CS8uCwc', 9, 4),
       ('AVAILABLE', 'QnxSjT4yC1', 9, 1),
       ('AVAILABLE', '732WnvJDVO', 9, 1),
       ('AVAILABLE', 'vTkoKCLDfe', 9, 4),
       ('AVAILABLE', 'FG0NAgo4P8', 9, 2),
       ('AVAILABLE', 'UuqZvag63C', 9, 4),
       ('AVAILABLE', 'TnNSMORaxp', 9, 2),
       ('AVAILABLE', 'Bk8XVIlUI8', 9, 4),
       ('AVAILABLE', 'XXArpkC4XB', 9, 4),
       ('AVAILABLE', 'RKa6IneA0I', 9, 3),
       ('AVAILABLE', 'uLKMmjee7y', 9, 1),
       ('AVAILABLE', '2LKKsseq2J', 9, 1),
       ('AVAILABLE', 'nhPbYzpXTi', 9, 1),
       ('AVAILABLE', 'pxq2QXvPOK', 9, 1),
       ('AVAILABLE', 'Bml7R2pKgV', 9, 1),
       ('AVAILABLE', 'BTnch40GRi', 9, 3),
       ('AVAILABLE', 'uuR2Sh7ijy', 9, 1),
       ('AVAILABLE', 'ctVUYVMPBX', 9, 1),
       ('AVAILABLE', 'jhbyIpPzFC', 9, 4),
       ('AVAILABLE', 'vmNQuvuKyx', 9, 2),
       ('AVAILABLE', 'OwosS50hsq', 9, 1),
       ('AVAILABLE', 'v9qP23hdMy', 9, 2),
       ('AVAILABLE', 'TUX4m8XmXT', 9, 3),
       ('AVAILABLE', 'Mi1KaDFLYr', 9, 1),
       ('AVAILABLE', 'RP0VZnhEe7', 9, 2),
       ('AVAILABLE', 'qNg3KweSOt', 9, 4),
       ('AVAILABLE', 'BdUgbxWSAk', 9, 1),
       ('AVAILABLE', 'GAIFoV6RpX', 9, 4),
       ('AVAILABLE', 'H2HczQD6E9', 9, 2),
       ('AVAILABLE', 'ANfsch3Adu', 9, 2),
       ('AVAILABLE', 'WqBZvn9w89', 9, 4),
       ('AVAILABLE', 'FUKUDxmhZk', 9, 3),
       ('AVAILABLE', 'clbwVE4IyW', 9, 3),
       ('AVAILABLE', 'Q0dUrVp3Ar', 9, 3),
       ('AVAILABLE', '1zwXz9CMha', 9, 3),
       ('AVAILABLE', 'TWEWAXF0z4', 9, 1),
       ('AVAILABLE', 'uQ1e3hOoci', 9, 3),
       ('AVAILABLE', 'sqdJLFvV6l', 9, 1),
       ('AVAILABLE', '5nmqMQcm5Q', 9, 1),
       ('AVAILABLE', 'OXgZHFhZYt', 10, 3),
       ('AVAILABLE', 'BCKANFEMAs', 10, 4),
       ('AVAILABLE', 'KrHN96X8Fz', 10, 3),
       ('AVAILABLE', 'amNylwqqhd', 10, 3),
       ('AVAILABLE', 'q1kO2Wz7YX', 10, 3),
       ('AVAILABLE', 'g47Blzl9cI', 10, 2),
       ('AVAILABLE', 'wMp6LN54i6', 10, 2),
       ('AVAILABLE', '2Ke6n0F8gi', 10, 2),
       ('AVAILABLE', '8TkXf99UN8', 10, 3),
       ('AVAILABLE', 'XJDLlFMfJd', 10, 3),
       ('AVAILABLE', 'IP3GZwtlCo', 10, 3),
       ('AVAILABLE', 'y1gs5aZo0P', 10, 1),
       ('AVAILABLE', 'YfO9Ibr4Pl', 10, 4),
       ('AVAILABLE', 'sV1d0wz1dL', 10, 2),
       ('AVAILABLE', 'Aig7t9KVYF', 10, 4),
       ('AVAILABLE', '6Xrd76F3bd', 10, 2),
       ('AVAILABLE', 'EMgbyDGlQH', 10, 2),
       ('AVAILABLE', 'MQ0m0XBQ2D', 10, 1),
       ('AVAILABLE', '1xd6V6ZV9j', 10, 2),
       ('AVAILABLE', 'nZ23YP8E97', 10, 1),
       ('AVAILABLE', 'sSxvfPYMXG', 10, 4),
       ('AVAILABLE', 't6oMjCCvVh', 11, 1),
       ('AVAILABLE', 'Am5NgkrY7t', 11, 2),
       ('AVAILABLE', 'Y0ZO2dk2XR', 11, 2),
       ('AVAILABLE', 'KrVE95qxFD', 11, 4),
       ('AVAILABLE', 'MvqV3uuTAy', 11, 3),
       ('AVAILABLE', 'D57D74nsYA', 11, 2),
       ('AVAILABLE', 'xH31tz3ZAO', 11, 2),
       ('AVAILABLE', 'nMovSQsMWa', 11, 4),
       ('AVAILABLE', 'gMC4TbUtzp', 11, 3),
       ('AVAILABLE', 'S3L5iWVhAD', 11, 2),
       ('AVAILABLE', '6uAuUOJXDz', 11, 1),
       ('AVAILABLE', '7it6Kwtdol', 11, 2),
       ('AVAILABLE', 'DMCMU0mb0d', 11, 2),
       ('AVAILABLE', 'PuONqRW8CO', 11, 4),
       ('AVAILABLE', 'N4vPqiXUnX', 11, 3),
       ('AVAILABLE', 'm1CJyWrbgy', 11, 3),
       ('AVAILABLE', 'rn0dAjCels', 11, 2),
       ('AVAILABLE', 'I0h2xBlO9s', 11, 2),
       ('AVAILABLE', '4OlGepA7SS', 11, 4),
       ('AVAILABLE', 'DjUYCHeeDJ', 11, 3),
       ('AVAILABLE', 'a1Y4QzGMaq', 11, 3),
       ('AVAILABLE', 'TkERrQSzW0', 11, 1),
       ('AVAILABLE', 'dT3mzEjted', 11, 4),
       ('AVAILABLE', 'Sihi9W0fmb', 11, 1),
       ('AVAILABLE', 'EyCCfGd5fD', 11, 1),
       ('AVAILABLE', 'nRvM8lLOeh', 11, 3),
       ('AVAILABLE', 'f2WfAzY6HD', 11, 3),
       ('AVAILABLE', 'KGdYXpOTZ3', 11, 1),
       ('AVAILABLE', 'jHOzmTnrKz', 11, 1),
       ('AVAILABLE', 's6dF03GY1z', 11, 4),
       ('AVAILABLE', 'VgosXeIZuW', 11, 1),
       ('AVAILABLE', 'fv02BKzqyX', 11, 3),
       ('AVAILABLE', 'K2HVn2kAE8', 11, 3),
       ('AVAILABLE', 'rJBjIRN7lL', 11, 2),
       ('AVAILABLE', 'yoJToiJ6ya', 11, 1),
       ('AVAILABLE', 'KOWbA52unT', 11, 3),
       ('AVAILABLE', 'nqzTpo5tPo', 12, 3),
       ('AVAILABLE', 'ja4xenSDdM', 12, 1),
       ('AVAILABLE', 'gL1hPhUGnI', 12, 2),
       ('AVAILABLE', 'kvnDzxHGYb', 12, 1),
       ('AVAILABLE', 'S2WiZ4MJ5C', 12, 3),
       ('AVAILABLE', 'i6HuFH5UFQ', 12, 3),
       ('AVAILABLE', 'sPOoSvFXKE', 12, 1),
       ('AVAILABLE', '9kxRQ7hBS9', 12, 1),
       ('AVAILABLE', 'YwXEbHDarN', 12, 2),
       ('AVAILABLE', 'EAmfNv7y2s', 12, 2),
       ('AVAILABLE', 'DO38TwNUY3', 12, 3),
       ('AVAILABLE', '9U740vtAls', 12, 4),
       ('AVAILABLE', 'NNImeXUs8z', 12, 4),
       ('AVAILABLE', 'Gl1zoP9Vrl', 12, 3),
       ('AVAILABLE', 'yPG9H7aSlZ', 12, 4),
       ('AVAILABLE', 'eR6RmkHQ7H', 12, 3),
       ('AVAILABLE', 'bnEpBuzGVQ', 12, 2),
       ('AVAILABLE', 't0MLUhwtyk', 12, 3),
       ('AVAILABLE', 'UXHUFktGGQ', 12, 1),
       ('AVAILABLE', 'Lvd5keyMeQ', 12, 4),
       ('AVAILABLE', '7z2ylKw0wQ', 12, 4),
       ('AVAILABLE', 'jrt8CImXSX', 12, 3),
       ('AVAILABLE', '6dERO37zMb', 12, 2),
       ('AVAILABLE', 'MNdxQTV3xp', 12, 2),
       ('AVAILABLE', 'cxHdn6lvb1', 12, 3),
       ('AVAILABLE', 'o47rzKJ2e4', 12, 3),
       ('AVAILABLE', 'SKpCHNS8aP', 12, 2),
       ('AVAILABLE', 'rdDyxZs22D', 12, 2),
       ('AVAILABLE', '6r1cWt32Gu', 12, 1),
       ('AVAILABLE', 'b72PbHc843', 12, 3),
       ('AVAILABLE', 'cJRmsKeeU3', 12, 1),
       ('AVAILABLE', '9vcs4kQNpi', 13, 2),
       ('AVAILABLE', 'MEP1hGGoEF', 13, 3),
       ('AVAILABLE', 'aMsvn85HMT', 13, 3),
       ('AVAILABLE', 'lmBPkjSjfs', 13, 2),
       ('AVAILABLE', '1jeLzuV8f5', 13, 2),
       ('AVAILABLE', 'NjbGuSCYdj', 13, 3),
       ('AVAILABLE', 'TU3EYYLobg', 13, 2),
       ('AVAILABLE', 'uLvlWuvLTU', 13, 1),
       ('AVAILABLE', 'aJ3N0Guzgo', 13, 4),
       ('AVAILABLE', 'ahjn6Ztb53', 13, 2),
       ('AVAILABLE', 'DcwacF9azB', 13, 3),
       ('AVAILABLE', 'WUDfeHktwO', 13, 4),
       ('AVAILABLE', 'zeq9sCbjGR', 13, 2),
       ('AVAILABLE', 'My2iPQVT53', 13, 1),
       ('AVAILABLE', 'GdnrHS6KK1', 13, 3),
       ('AVAILABLE', '2yS6z9vNr8', 13, 1),
       ('AVAILABLE', 'qnEpFcr8sx', 13, 3),
       ('AVAILABLE', 'zmkzihRbmW', 13, 2),
       ('AVAILABLE', '36CvS0ryRm', 13, 2),
       ('AVAILABLE', 'l8yc76NJmj', 13, 1),
       ('AVAILABLE', '7qhPBdFySN', 13, 4),
       ('AVAILABLE', '6HdzXV7OPc', 13, 4),
       ('AVAILABLE', 'qwtFAid6OL', 13, 4),
       ('AVAILABLE', 'ND77t8jSVF', 14, 1),
       ('AVAILABLE', '1P8irMo2PN', 14, 4),
       ('AVAILABLE', 'Y58U1QJk8U', 14, 2),
       ('AVAILABLE', 'eLwXEpanXC', 14, 4),
       ('AVAILABLE', 'Joq4slfMnG', 14, 2),
       ('AVAILABLE', 'h8AIgwDmHq', 14, 1),
       ('AVAILABLE', 'weAD3g7A7f', 14, 2),
       ('AVAILABLE', 'QPL35AtEAA', 14, 4),
       ('AVAILABLE', 'goK5t1Yt2i', 14, 1),
       ('AVAILABLE', 'RqihT6tYmL', 14, 4),
       ('AVAILABLE', 'buCfkoNdoa', 14, 2),
       ('AVAILABLE', 'DfHgtPDp0Z', 14, 2),
       ('AVAILABLE', '4AVuRc58Ly', 14, 2),
       ('AVAILABLE', 'ariuVaAKvb', 14, 3),
       ('AVAILABLE', 'vKmZlokvHQ', 14, 3),
       ('AVAILABLE', 'xsKctBR3zS', 14, 4),
       ('AVAILABLE', 'i1On2hI8sH', 14, 2),
       ('AVAILABLE', 'oEyWNs29XP', 14, 3),
       ('AVAILABLE', 'W4zbNHxOzS', 14, 2),
       ('AVAILABLE', 'LAfLR8hNrS', 14, 1),
       ('AVAILABLE', '0qeXMGdiH1', 14, 1),
       ('AVAILABLE', 'W7Ok5JQOnL', 14, 3),
       ('AVAILABLE', 'nRa1bWrQEE', 14, 4),
       ('AVAILABLE', 'V5JicV59fF', 14, 1),
       ('AVAILABLE', 'DQyAufL6Lp', 14, 3),
       ('AVAILABLE', 'YYefUiDXPi', 14, 3),
       ('AVAILABLE', 'VVCCs9qKnT', 14, 3),
       ('AVAILABLE', 'uLH2uFpg8X', 14, 2),
       ('AVAILABLE', 'X1YkzvnDHr', 14, 4),
       ('AVAILABLE', '9zmNUUXlq0', 14, 4);

-- RentalStationItem
INSERT INTO rental_station_items (stock, rental_station_id, rental_item_type_id)
VALUES (14, 1, 1),
       (13, 1, 2),
       (8, 1, 3),
       (8, 1, 4),
       (6, 1, 5),
       (9, 1, 6),
       (4, 1, 7),
       (8, 1, 8),
       (15, 1, 9),
       (3, 1, 10),
       (9, 1, 11),
       (7, 1, 12),
       (4, 1, 13),
       (6, 1, 14),
       (7, 4, 1),
       (13, 4, 2),
       (8, 4, 3),
       (9, 4, 4),
       (7, 4, 5),
       (2, 4, 6),
       (9, 4, 7),
       (4, 4, 8),
       (9, 4, 9),
       (4, 4, 10),
       (6, 4, 11),
       (5, 4, 12),
       (5, 4, 13),
       (8, 4, 14),
       (7, 3, 1),
       (12, 3, 2),
       (7, 3, 3),
       (7, 3, 4),
       (5, 3, 5),
       (3, 3, 6),
       (6, 3, 7),
       (8, 3, 8),
       (8, 3, 9),
       (7, 3, 10),
       (11, 3, 11),
       (11, 3, 12),
       (6, 3, 13),
       (7, 3, 14),
       (9, 2, 1),
       (11, 2, 2),
       (4, 2, 3),
       (12, 2, 4),
       (3, 2, 5),
       (11, 2, 6),
       (5, 2, 7),
       (3, 2, 8),
       (9, 2, 9),
       (7, 2, 10),
       (10, 2, 11),
       (8, 2, 12),
       (8, 2, 13),
       (9, 2, 14);

-- RentalHistory
INSERT INTO rental_history (status, rental_time_hour, start_time, end_time, return_time, token, user_id, rental_item_id,
                            rental_station_id, return_station_id)
VALUES ('RETURN', '1', '2025-02-18 08:52:49', '2025-02-18 09:52:49', '2025-02-18 09:52:49', 'kbVYNL5oMi', '1', '53', '4', '1'),
       ('RETURN', '2', '2025-02-04 07:52:49', '2025-02-04 09:52:49', '2025-02-04 09:52:49', 'AT7OZHHzd7', '1', '42', '3', '3'),
       ('RETURN', '3', '2025-02-19 04:52:49', '2025-02-19 07:52:49', '2025-02-19 07:52:49', 'GPjDG2WKs8', '1', '31', '3', '2'),
       ('RETURN', '4', '2025-02-11 02:52:49', '2025-02-11 06:52:49', '2025-02-11 06:52:49', 'UcZKeTsK2M', '1', '81', '3', '4'),
       ('RETURN', '5', '2025-02-01 02:52:49', '2025-02-01 07:52:49', '2025-02-01 07:52:49', 'D1rnFY33zX', '1', '60', '3', '1'),
       ('RETURN', '1', '2025-02-13 09:52:49', '2025-02-13 10:52:49', '2025-02-13 10:52:49', '944ugQnPy3', '1', '30', '3', '1'),
       ('RETURN', '2', '2025-02-01 11:52:49', '2025-02-01 13:52:49', '2025-02-01 13:52:49', 'ZJsvUhQ2wZ', '1', '21', '3', '4'),
       ('RETURN', '3', '2025-02-20 01:52:49', '2025-02-20 04:52:49', '2025-02-20 04:52:49', 'W6JjVQZPE8', '1', '14', '4', '4'),
       ('RETURN', '4', '2025-02-21 03:52:49', '2025-02-21 07:52:49', '2025-02-21 07:52:49', 'n5LBG6OfTp', '1', '12', '2', '1'),
       ('RETURN', '5', '2025-01-31 08:52:49', '2025-01-31 13:52:49', '2025-01-31 13:52:49', '97Jq1t5v9D', '1', '26', '2', '1'),
       ('RETURN', '1', '2025-01-31 01:52:49', '2025-01-31 02:52:49', '2025-01-31 02:52:49', 'm12JIiMZQC', '1', '53', '3', '1'),
       ('RETURN', '2', '2025-02-19 08:52:49', '2025-02-19 10:52:49', '2025-02-19 10:52:49', 'ZEarbPsU0V', '1', '1', '4', '3'),
       ('RETURN', '3', '2025-02-05 09:52:49', '2025-02-05 12:52:49', '2025-02-05 12:52:49', 'm0OAJncTgx', '1', '43', '3', '1'),
       ('RETURN', '4', '2025-02-17 00:52:49', '2025-02-17 04:52:49', '2025-02-17 04:52:49', 'RdHtn5DBdA', '1', '72', '3', '1'),
       ('RETURN', '5', '2025-02-23 09:52:49', '2025-02-23 14:52:49', '2025-02-23 14:52:49', '5lpA7qPtWY', '1', '26', '1', '4'),
       ('RETURN', '1', '2025-02-22 01:52:49', '2025-02-22 02:52:49', '2025-02-22 02:52:49', 'UfArFO0Mew', '1', '24', '3', '3'),
       ('RETURN', '2', '2025-01-30 08:52:49', '2025-01-30 10:52:49', '2025-01-30 10:52:49', 'fCqjoGzPRC', '1', '14', '3', '1'),
       ('RETURN', '3', '2025-02-26 00:52:49', '2025-02-26 03:52:49', '2025-02-26 03:52:49', '3ZArZ77WUG', '1', '25', '4', '3'),
       ('RETURN', '4', '2025-02-16 06:52:49', '2025-02-16 10:52:49', '2025-02-16 10:52:49', 'cDVq2UKtjJ', '1', '72', '3', '1'),
       ('RETURN', '5', '2025-02-03 05:52:49', '2025-02-03 10:52:49', '2025-02-03 10:52:49', '58p7CHCDbS', '1', '20', '2', '2'),
       ('RETURN', '1', '2025-02-07 07:52:49', '2025-02-07 08:52:49', '2025-02-07 08:52:49', 'MmcrHi0yAE', '1', '37', '4', '1'),
       ('RETURN', '2', '2025-01-30 03:52:49', '2025-01-30 05:52:49', '2025-01-30 05:52:49', 'Y30owGfW3r', '1', '84', '2', '4'),
       ('RETURN', '3', '2025-02-27 00:52:49', '2025-02-27 03:52:49', '2025-02-27 03:52:49', '5LjGQX4yrb', '1', '13', '1', '2'),
       ('RETURN', '4', '2025-02-11 05:52:49', '2025-02-11 09:52:49', '2025-02-11 09:52:49', 'Z9DGGrCPvk', '1', '5', '1', '1'),
       ('RETURN', '5', '2025-02-12 07:52:49', '2025-02-12 12:52:49', '2025-02-12 12:52:49', 'TF43vFRtv0', '1', '28', '4', '2'),
       ('RETURN', '1', '2025-01-31 07:52:49', '2025-01-31 08:52:49', '2025-01-31 08:52:49', 'WZzch607a4', '1', '97', '4', '4'),
       ('RETURN', '2', '2025-02-01 01:52:49', '2025-02-01 03:52:49', '2025-02-01 03:52:49', 'hJfNf73c94', '1', '25', '3', '3'),
       ('RETURN', '3', '2025-02-11 03:52:49', '2025-02-11 06:52:49', '2025-02-11 06:52:49', 'kN6DMxhO9j', '1', '21', '1', '1'),
       ('RENTAL', '4', '2025-02-28 10:00:00', '2025-02-28 14:00:00', NULL, '9ddkgLrmLz', '1', '100', '3', '3'),
       ('RENTAL', '5', '2025-03-01 14:00:00', '2025-03-01 19:00:00', NULL, 'XzLoXbmLVf', '1', '63', '4', '4');