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
VALUES ('AVAILABLE', 'RI_kCQzZkTC20', 1, 1),
       ('AVAILABLE', 'RI_PsT51cpJ8t', 1, 4),
       ('AVAILABLE', 'RI_Z2ZUjXO3tG', 1, 3),
       ('AVAILABLE', 'RI_lcuSauqWri', 1, 1),
       ('AVAILABLE', 'RI_p9Tq1UovdT', 1, 2),
       ('AVAILABLE', 'RI_451nYXs2aq', 1, 1),
       ('AVAILABLE', 'RI_cQuMzqJwYa', 1, 4),
       ('AVAILABLE', 'RI_NAt5iQv03a', 1, 1),
       ('AVAILABLE', 'RI_QMwQAOl9HE', 1, 2),
       ('AVAILABLE', 'RI_wYwoNTLmlG', 1, 4),
       ('AVAILABLE', 'RI_Y45PftSqvF', 1, 1),
       ('AVAILABLE', 'RI_lefbpYaqQf', 1, 3),
       ('AVAILABLE', 'RI_FjXeKC6hzV', 1, 3),
       ('AVAILABLE', 'RI_p0DvYzVTqh', 1, 2),
       ('AVAILABLE', 'RI_NMTWrxKFR7', 1, 4),
       ('AVAILABLE', 'RI_RXVNYqMMTt', 1, 1),
       ('AVAILABLE', 'RI_PowZxOG87S', 1, 2),
       ('AVAILABLE', 'RI_d4SE67iFkl', 1, 2),
       ('AVAILABLE', 'RI_u5ugVVtXn3', 1, 2),
       ('AVAILABLE', 'RI_wrRrcYBpcx', 1, 1),
       ('AVAILABLE', 'RI_5HHBrGaFjt', 1, 1),
       ('AVAILABLE', 'RI_oUGPXrKI5Y', 1, 1),
       ('AVAILABLE', 'RI_pRpD68Ctvh', 1, 3),
       ('AVAILABLE', 'RI_v0ixn8GV8g', 1, 2),
       ('AVAILABLE', 'RI_TMeLFEcuro', 1, 4),
       ('AVAILABLE', 'RI_cQe1oMebLS', 1, 4),
       ('AVAILABLE', 'RI_ckCYn4w38l', 1, 3),
       ('AVAILABLE', 'RI_ZrNBLtpqFA', 1, 1),
       ('AVAILABLE', 'RI_hxbd9X70vX', 1, 2),
       ('AVAILABLE', 'RI_mYKJdUjYQB', 1, 3),
       ('AVAILABLE', 'RI_1KN17pyFIn', 1, 1),
       ('AVAILABLE', 'RI_pPy6IpcoiV', 1, 1),
       ('AVAILABLE', 'RI_uU7ukCOkrG', 1, 3),
       ('AVAILABLE', 'RI_4iNU6zffIo', 1, 4),
       ('AVAILABLE', 'RI_S4v1y4aNeY', 1, 1),
       ('AVAILABLE', 'RI_2vpZ0L1WLX', 1, 2),
       ('AVAILABLE', 'RI_e1AQsUxQvn', 1, 1),
       ('AVAILABLE', 'RI_ugvzy4pwOu', 2, 1),
       ('AVAILABLE', 'RI_IjABGwO7k1', 2, 3),
       ('AVAILABLE', 'RI_mCRf9IzkSB', 2, 1),
       ('AVAILABLE', 'RI_YwHVAm9JUq', 2, 4),
       ('AVAILABLE', 'RI_GaZsc3EPPs', 2, 3),
       ('AVAILABLE', 'RI_W1IjmB3oCf', 2, 2),
       ('AVAILABLE', 'RI_3B8sv2zXFu', 2, 2),
       ('AVAILABLE', 'RI_ZHIdFR9LPp', 2, 2),
       ('AVAILABLE', 'RI_EYpOno4AEC', 2, 2),
       ('AVAILABLE', 'RI_eHhNOIxid8', 2, 3),
       ('AVAILABLE', 'RI_tZjcDOJmeI', 2, 2),
       ('AVAILABLE', 'RI_WX7zqNAXis', 2, 4),
       ('AVAILABLE', 'RI_4xHbF3gjbJ', 2, 4),
       ('AVAILABLE', 'RI_UvKrSdWltv', 2, 4),
       ('AVAILABLE', 'RI_qVPprRdNIJ', 2, 1),
       ('AVAILABLE', 'RI_rw5oiRxHs1', 2, 2),
       ('AVAILABLE', 'RI_Msp9ZL4rcL', 2, 1),
       ('AVAILABLE', 'RI_xxa75Gtwx6', 2, 3),
       ('AVAILABLE', 'RI_CKYFUjpvJB', 2, 3),
       ('AVAILABLE', 'RI_cH8s8VBnxs', 2, 1),
       ('AVAILABLE', 'RI_OdSLFIE0s0', 2, 3),
       ('AVAILABLE', 'RI_Gd27dR85mD', 2, 3),
       ('AVAILABLE', 'RI_wsmR1AsaNR', 2, 2),
       ('AVAILABLE', 'RI_FWiduaOI7Y', 2, 1),
       ('AVAILABLE', 'RI_9QjM4Hy3Yd', 2, 4),
       ('AVAILABLE', 'RI_9uLp8O1vyh', 2, 2),
       ('AVAILABLE', 'RI_3VsQP1Fy1p', 2, 3),
       ('AVAILABLE', 'RI_42Ol5rvMlp', 2, 2),
       ('AVAILABLE', 'RI_snDFoLFQkl', 2, 4),
       ('AVAILABLE', 'RI_Aq05b357QD', 2, 3),
       ('AVAILABLE', 'RI_Y7rTBsZx3z', 2, 2),
       ('AVAILABLE', 'RI_E2uIn48JAz', 2, 1),
       ('AVAILABLE', 'RI_tnByMgOHXx', 2, 4),
       ('AVAILABLE', 'RI_3zGooPZ1Bw', 2, 2),
       ('AVAILABLE', 'RI_1lsDwAzcNO', 2, 4),
       ('AVAILABLE', 'RI_XfkZQPKl7O', 2, 4),
       ('AVAILABLE', 'RI_9mkwCSMCnl', 2, 4),
       ('AVAILABLE', 'RI_HY1C1j804N', 2, 1),
       ('AVAILABLE', 'RI_StjAxbjk7X', 2, 3),
       ('AVAILABLE', 'RI_bnibl4Qmnw', 2, 1),
       ('AVAILABLE', 'RI_QoNWmrmHGe', 2, 1),
       ('AVAILABLE', 'RI_4TvpOSueio', 2, 4),
       ('AVAILABLE', 'RI_EJ5dA0aPt1', 2, 4),
       ('AVAILABLE', 'RI_5zQb0C2cDs', 2, 1),
       ('AVAILABLE', 'RI_DlzC904BT7', 2, 4),
       ('AVAILABLE', 'RI_rPJ3klTfpe', 2, 3),
       ('AVAILABLE', 'RI_afBcn4o4tA', 2, 3),
       ('AVAILABLE', 'RI_9WZ1eWS6PR', 2, 1),
       ('AVAILABLE', 'RI_9yH7JHCsQ6', 2, 1),
       ('AVAILABLE', 'RI_q6UgYEnjYR', 3, 1),
       ('AVAILABLE', 'RI_09hkLHi7C6', 3, 4),
       ('AVAILABLE', 'RI_pWCxZ3OWH7', 3, 4),
       ('AVAILABLE', 'RI_lVgyMfI27t', 3, 4),
       ('AVAILABLE', 'RI_f219WxfENd', 3, 1),
       ('AVAILABLE', 'RI_gkPL91MsJ1', 3, 2),
       ('AVAILABLE', 'RI_O7ZEtynEnR', 3, 2),
       ('AVAILABLE', 'RI_dhKrzEPM6K', 3, 3),
       ('AVAILABLE', 'RI_YUira9BNZh', 3, 3),
       ('AVAILABLE', 'RI_gCqLFStrkZ', 3, 3),
       ('AVAILABLE', 'RI_5VMrK6vtsm', 3, 1),
       ('AVAILABLE', 'RI_zqmi14HevS', 3, 2),
       ('AVAILABLE', 'RI_DjK5L9kkiN', 3, 4),
       ('AVAILABLE', 'RI_mc1ziURLg4', 3, 1),
       ('AVAILABLE', 'RI_sxMGpTFExs', 3, 1),
       ('AVAILABLE', 'RI_SISKzxlvwr', 3, 3),
       ('AVAILABLE', 'RI_pW8j3hRjAG', 3, 3),
       ('AVAILABLE', 'RI_bdFLZJWrMu', 3, 4),
       ('AVAILABLE', 'RI_q8MePGfiez', 3, 4),
       ('AVAILABLE', 'RI_pawiOr2nFL', 3, 4),
       ('AVAILABLE', 'RI_43Itd7SdWJ', 3, 1),
       ('AVAILABLE', 'RI_LB71LPuIr4', 3, 4),
       ('AVAILABLE', 'RI_9U3YHEfPX4', 3, 1),
       ('AVAILABLE', 'RI_hvQThlCwQt', 3, 3),
       ('AVAILABLE', 'RI_Kc7xy0Avrm', 3, 3),
       ('AVAILABLE', 'RI_M0sLU41k84', 3, 1),
       ('AVAILABLE', 'RI_mRZtnWF3qq', 3, 2),
       ('AVAILABLE', 'RI_6YzdtWjtjK', 4, 4),
       ('AVAILABLE', 'RI_L6S1PvmVLw', 4, 2),
       ('AVAILABLE', 'RI_uKHA7Hf2is', 4, 4),
       ('AVAILABLE', 'RI_EniWUx0VtM', 4, 3),
       ('AVAILABLE', 'RI_vPnMwqf9uq', 4, 4),
       ('AVAILABLE', 'RI_a4ewUa13tI', 4, 4),
       ('AVAILABLE', 'RI_Zj8OhF5RDJ', 4, 2),
       ('AVAILABLE', 'RI_aBvpIX1XKs', 4, 3),
       ('AVAILABLE', 'RI_IPQ58qKurJ', 4, 1),
       ('AVAILABLE', 'RI_h1tsmv59Pp', 4, 1),
       ('AVAILABLE', 'RI_ShCL50AlKv', 4, 2),
       ('AVAILABLE', 'RI_Gmhr4XfHdJ', 4, 3),
       ('AVAILABLE', 'RI_Yu5paRVuPJ', 4, 2),
       ('AVAILABLE', 'RI_DoTzJA9g04', 4, 1),
       ('AVAILABLE', 'RI_Lcsr9Qy4nW', 4, 3),
       ('AVAILABLE', 'RI_KHK9jEGCHA', 4, 2),
       ('AVAILABLE', 'RI_z6LtxFSYxq', 4, 2),
       ('AVAILABLE', 'RI_YdTjmUF5e3', 4, 4),
       ('AVAILABLE', 'RI_6lBtGDq8Zr', 4, 2),
       ('AVAILABLE', 'RI_vPQAZpFf3Q', 4, 3),
       ('AVAILABLE', 'RI_4IYSHYqRkz', 4, 2),
       ('AVAILABLE', 'RI_gTdVbN0u3H', 4, 4),
       ('AVAILABLE', 'RI_uQq9fjherN', 4, 2),
       ('AVAILABLE', 'RI_T6GbHu4ozW', 4, 1),
       ('AVAILABLE', 'RI_LsGjQMlEJt', 4, 1),
       ('AVAILABLE', 'RI_iQVGBNpLot', 4, 1),
       ('AVAILABLE', 'RI_54lTQGciFk', 4, 1),
       ('AVAILABLE', 'RI_Vp98qYpyef', 4, 1),
       ('AVAILABLE', 'RI_pJLccbB4fr', 4, 4),
       ('AVAILABLE', 'RI_hjk0y6U8Fb', 4, 2),
       ('AVAILABLE', 'RI_NewlNNN1IN', 4, 3),
       ('AVAILABLE', 'RI_0OsR2a9MpC', 4, 4),
       ('AVAILABLE', 'RI_YTMg9APYUX', 4, 4),
       ('AVAILABLE', 'RI_L9ObbpvvKb', 4, 3),
       ('AVAILABLE', 'RI_C0Eu6YBQs1', 4, 2),
       ('AVAILABLE', 'RI_zRtEyXXyll', 4, 2),
       ('AVAILABLE', 'RI_IedyrN6Ncu', 5, 4),
       ('AVAILABLE', 'RI_dlX9ahOrBV', 5, 4),
       ('AVAILABLE', 'RI_EMudt5OB8n', 5, 1),
       ('AVAILABLE', 'RI_UAi2gHTAFa', 5, 4),
       ('AVAILABLE', 'RI_uqGQ0wgB0h', 5, 4),
       ('AVAILABLE', 'RI_a2aR3VPkC9', 5, 4),
       ('AVAILABLE', 'RI_gbrE3C3Xd3', 5, 2),
       ('AVAILABLE', 'RI_9K9mDks5Cy', 5, 4),
       ('AVAILABLE', 'RI_B6BoBNSd3w', 5, 4),
       ('AVAILABLE', 'RI_r6NI812tLt', 5, 1),
       ('AVAILABLE', 'RI_Zryoq2op8b', 5, 1),
       ('AVAILABLE', 'RI_0RI6i7OZtE', 5, 3),
       ('AVAILABLE', 'RI_G6utkAOoPo', 5, 2),
       ('AVAILABLE', 'RI_SXSt8Go9KR', 5, 1),
       ('AVAILABLE', 'RI_esBEM5k85J', 5, 1),
       ('AVAILABLE', 'RI_h67Xs72uHG', 5, 3),
       ('AVAILABLE', 'RI_hHXC10emdr', 5, 3),
       ('AVAILABLE', 'RI_7ayH72YiTw', 5, 3),
       ('AVAILABLE', 'RI_vPvlU9PNFH', 5, 2),
       ('AVAILABLE', 'RI_MZ7Bb0YKca', 5, 1),
       ('AVAILABLE', 'RI_HX9ymrHhwg', 5, 3),
       ('AVAILABLE', 'RI_1nDxiV2QAd', 6, 1),
       ('AVAILABLE', 'RI_DfOD8mV2Rh', 6, 4),
       ('AVAILABLE', 'RI_fY0seW2V2e', 6, 1),
       ('AVAILABLE', 'RI_JC1Jt6y5KQ', 6, 2),
       ('AVAILABLE', 'RI_VVurL49wdt', 6, 2),
       ('AVAILABLE', 'RI_RH3gTnAYhU', 6, 1),
       ('AVAILABLE', 'RI_E0GHAn4t3E', 6, 1),
       ('AVAILABLE', 'RI_VwJD7HyylZ', 6, 2),
       ('AVAILABLE', 'RI_elwtrUXC3Y', 6, 2),
       ('AVAILABLE', 'RI_MIP4MjnUV7', 6, 1),
       ('AVAILABLE', 'RI_kwqOUyN89K', 6, 2),
       ('AVAILABLE', 'RI_49N93zCRep', 6, 1),
       ('AVAILABLE', 'RI_V1ercJW85d', 6, 2),
       ('AVAILABLE', 'RI_4u3XH4FPuL', 6, 3),
       ('AVAILABLE', 'RI_Nn6zva5dC7', 6, 1),
       ('AVAILABLE', 'RI_eibkuv94bN', 6, 2),
       ('AVAILABLE', 'RI_I9KL0JIqvG', 6, 3),
       ('AVAILABLE', 'RI_blCPJ4UX2J', 6, 1),
       ('AVAILABLE', 'RI_2h5J4nRtzX', 6, 2),
       ('AVAILABLE', 'RI_CLMjwknrHh', 6, 2),
       ('AVAILABLE', 'RI_jegCQMyv6o', 6, 1),
       ('AVAILABLE', 'RI_MGx4UNCnWv', 6, 4),
       ('AVAILABLE', 'RI_LeiyD2NBpN', 6, 2),
       ('AVAILABLE', 'RI_rK6Wgj5QVK', 6, 3),
       ('AVAILABLE', 'RI_EJnXiTGMZn', 6, 2),
       ('AVAILABLE', 'RI_eC9gPQ5p2t', 7, 3),
       ('AVAILABLE', 'RI_JIw7jWIgL2', 7, 1),
       ('AVAILABLE', 'RI_HpmteOrEan', 7, 1),
       ('AVAILABLE', 'RI_YOKEXxrnEu', 7, 4),
       ('AVAILABLE', 'RI_bbYXeBPbaG', 7, 2),
       ('AVAILABLE', 'RI_6Qc3krJLSp', 7, 2),
       ('AVAILABLE', 'RI_YTcPGRo1Zy', 7, 3),
       ('AVAILABLE', 'RI_Nptb2ieiho', 7, 3),
       ('AVAILABLE', 'RI_3vuz1xGjEv', 7, 4),
       ('AVAILABLE', 'RI_KjiBatgJ8f', 7, 2),
       ('AVAILABLE', 'RI_y634uxA49o', 7, 4),
       ('AVAILABLE', 'RI_D74Gc95C5x', 7, 4),
       ('AVAILABLE', 'RI_yMvE6VEXF7', 7, 4),
       ('AVAILABLE', 'RI_hAJQuC8mPA', 7, 4),
       ('AVAILABLE', 'RI_XgVZwiPxmD', 7, 1),
       ('AVAILABLE', 'RI_4eL06rFC8e', 7, 3),
       ('AVAILABLE', 'RI_8kWRYFzYRQ', 7, 2),
       ('AVAILABLE', 'RI_SPDgyxt8t1', 7, 4),
       ('AVAILABLE', 'RI_YR1Tf7t0UF', 7, 4),
       ('AVAILABLE', 'RI_4JhIiEmV72', 7, 4),
       ('AVAILABLE', 'RI_OcgTeOnIbu', 7, 2),
       ('AVAILABLE', 'RI_7QZ2F8x2VD', 7, 1),
       ('AVAILABLE', 'RI_NXYzaQHWin', 7, 3),
       ('AVAILABLE', 'RI_A6IafECH5U', 7, 3),
       ('AVAILABLE', 'RI_TZLsnGTISP', 8, 1),
       ('AVAILABLE', 'RI_hAOpATuysl', 8, 4),
       ('AVAILABLE', 'RI_ileCOJHdTu', 8, 3),
       ('AVAILABLE', 'RI_2RsZtZmeqR', 8, 1),
       ('AVAILABLE', 'RI_ZiZ1DFAPIc', 8, 3),
       ('AVAILABLE', 'RI_XEBEbXtaXV', 8, 3),
       ('AVAILABLE', 'RI_u5Hf56mQCX', 8, 4),
       ('AVAILABLE', 'RI_Jvyy3HYSZa', 8, 1),
       ('AVAILABLE', 'RI_vDmcgZ64Ls', 8, 2),
       ('AVAILABLE', 'RI_QwvLKjWZQZ', 8, 3),
       ('AVAILABLE', 'RI_elouXiwoig', 8, 1),
       ('AVAILABLE', 'RI_ZoMqSbN9CU', 8, 2),
       ('AVAILABLE', 'RI_TGXbPyoqa3', 8, 3),
       ('AVAILABLE', 'RI_vJfxkV5MWu', 8, 4),
       ('AVAILABLE', 'RI_cWLBvtBT2g', 8, 2),
       ('AVAILABLE', 'RI_sH3ISUkBPQ', 8, 1),
       ('AVAILABLE', 'RI_90aSgVFrWd', 8, 1),
       ('AVAILABLE', 'RI_0QEU2Exz1L', 8, 1),
       ('AVAILABLE', 'RI_theEhjZ6dW', 8, 1),
       ('AVAILABLE', 'RI_YeNQNBpf1Y', 8, 3),
       ('AVAILABLE', 'RI_NJykS5kX2o', 8, 4),
       ('AVAILABLE', 'RI_wT2VTnCYFw', 8, 3),
       ('AVAILABLE', 'RI_8byMmaZTTd', 8, 3),
       ('AVAILABLE', 'RI_CQPqUW5HPe', 9, 2),
       ('AVAILABLE', 'RI_h3jyK5Uh13', 9, 2),
       ('AVAILABLE', 'RI_L17CS8uCwc', 9, 4),
       ('AVAILABLE', 'RI_QnxSjT4yC1', 9, 1),
       ('AVAILABLE', 'RI_732WnvJDVO', 9, 1),
       ('AVAILABLE', 'RI_vTkoKCLDfe', 9, 4),
       ('AVAILABLE', 'RI_FG0NAgo4P8', 9, 2),
       ('AVAILABLE', 'RI_UuqZvag63C', 9, 4),
       ('AVAILABLE', 'RI_TnNSMORaxp', 9, 2),
       ('AVAILABLE', 'RI_Bk8XVIlUI8', 9, 4),
       ('AVAILABLE', 'RI_XXArpkC4XB', 9, 4),
       ('AVAILABLE', 'RI_RKa6IneA0I', 9, 3),
       ('AVAILABLE', 'RI_uLKMmjee7y', 9, 1),
       ('AVAILABLE', 'RI_2LKKsseq2J', 9, 1),
       ('AVAILABLE', 'RI_nhPbYzpXTi', 9, 1),
       ('AVAILABLE', 'RI_pxq2QXvPOK', 9, 1),
       ('AVAILABLE', 'RI_Bml7R2pKgV', 9, 1),
       ('AVAILABLE', 'RI_BTnch40GRi', 9, 3),
       ('AVAILABLE', 'RI_uuR2Sh7ijy', 9, 1),
       ('AVAILABLE', 'RI_ctVUYVMPBX', 9, 1),
       ('AVAILABLE', 'RI_jhbyIpPzFC', 9, 4),
       ('AVAILABLE', 'RI_vmNQuvuKyx', 9, 2),
       ('AVAILABLE', 'RI_OwosS50hsq', 9, 1),
       ('AVAILABLE', 'RI_v9qP23hdMy', 9, 2),
       ('AVAILABLE', 'RI_TUX4m8XmXT', 9, 3),
       ('AVAILABLE', 'RI_Mi1KaDFLYr', 9, 1),
       ('AVAILABLE', 'RI_RP0VZnhEe7', 9, 2),
       ('AVAILABLE', 'RI_qNg3KweSOt', 9, 4),
       ('AVAILABLE', 'RI_BdUgbxWSAk', 9, 1),
       ('AVAILABLE', 'RI_GAIFoV6RpX', 9, 4),
       ('AVAILABLE', 'RI_H2HczQD6E9', 9, 2),
       ('AVAILABLE', 'RI_ANfsch3Adu', 9, 2),
       ('AVAILABLE', 'RI_WqBZvn9w89', 9, 4),
       ('AVAILABLE', 'RI_FUKUDxmhZk', 9, 3),
       ('AVAILABLE', 'RI_clbwVE4IyW', 9, 3),
       ('AVAILABLE', 'RI_Q0dUrVp3Ar', 9, 3),
       ('AVAILABLE', 'RI_1zwXz9CMha', 9, 3),
       ('AVAILABLE', 'RI_TWEWAXF0z4', 9, 1),
       ('AVAILABLE', 'RI_uQ1e3hOoci', 9, 3),
       ('AVAILABLE', 'RI_sqdJLFvV6l', 9, 1),
       ('AVAILABLE', 'RI_5nmqMQcm5Q', 9, 1),
       ('AVAILABLE', 'RI_OXgZHFhZYt', 10, 3),
       ('AVAILABLE', 'RI_BCKANFEMAs', 10, 4),
       ('AVAILABLE', 'RI_KrHN96X8Fz', 10, 3),
       ('AVAILABLE', 'RI_amNylwqqhd', 10, 3),
       ('AVAILABLE', 'RI_q1kO2Wz7YX', 10, 3),
       ('AVAILABLE', 'RI_g47Blzl9cI', 10, 2),
       ('AVAILABLE', 'RI_wMp6LN54i6', 10, 2),
       ('AVAILABLE', 'RI_2Ke6n0F8gi', 10, 2),
       ('AVAILABLE', 'RI_8TkXf99UN8', 10, 3),
       ('AVAILABLE', 'RI_XJDLlFMfJd', 10, 3),
       ('AVAILABLE', 'RI_IP3GZwtlCo', 10, 3),
       ('AVAILABLE', 'RI_y1gs5aZo0P', 10, 1),
       ('AVAILABLE', 'RI_YfO9Ibr4Pl', 10, 4),
       ('AVAILABLE', 'RI_sV1d0wz1dL', 10, 2),
       ('AVAILABLE', 'RI_Aig7t9KVYF', 10, 4),
       ('AVAILABLE', 'RI_6Xrd76F3bd', 10, 2),
       ('AVAILABLE', 'RI_EMgbyDGlQH', 10, 2),
       ('AVAILABLE', 'RI_MQ0m0XBQ2D', 10, 1),
       ('AVAILABLE', 'RI_1xd6V6ZV9j', 10, 2),
       ('AVAILABLE', 'RI_nZ23YP8E97', 10, 1),
       ('AVAILABLE', 'RI_sSxvfPYMXG', 10, 4),
       ('AVAILABLE', 'RI_t6oMjCCvVh', 11, 1),
       ('AVAILABLE', 'RI_Am5NgkrY7t', 11, 2),
       ('AVAILABLE', 'RI_Y0ZO2dk2XR', 11, 2),
       ('AVAILABLE', 'RI_KrVE95qxFD', 11, 4),
       ('AVAILABLE', 'RI_MvqV3uuTAy', 11, 3),
       ('AVAILABLE', 'RI_D57D74nsYA', 11, 2),
       ('AVAILABLE', 'RI_xH31tz3ZAO', 11, 2),
       ('AVAILABLE', 'RI_nMovSQsMWa', 11, 4),
       ('AVAILABLE', 'RI_gMC4TbUtzp', 11, 3),
       ('AVAILABLE', 'RI_S3L5iWVhAD', 11, 2),
       ('AVAILABLE', 'RI_6uAuUOJXDz', 11, 1),
       ('AVAILABLE', 'RI_7it6Kwtdol', 11, 2),
       ('AVAILABLE', 'RI_DMCMU0mb0d', 11, 2),
       ('AVAILABLE', 'RI_PuONqRW8CO', 11, 4),
       ('AVAILABLE', 'RI_N4vPqiXUnX', 11, 3),
       ('AVAILABLE', 'RI_m1CJyWrbgy', 11, 3),
       ('AVAILABLE', 'RI_rn0dAjCels', 11, 2),
       ('AVAILABLE', 'RI_I0h2xBlO9s', 11, 2),
       ('AVAILABLE', 'RI_4OlGepA7SS', 11, 4),
       ('AVAILABLE', 'RI_DjUYCHeeDJ', 11, 3),
       ('AVAILABLE', 'RI_a1Y4QzGMaq', 11, 3),
       ('AVAILABLE', 'RI_TkERrQSzW0', 11, 1),
       ('AVAILABLE', 'RI_dT3mzEjted', 11, 4),
       ('AVAILABLE', 'RI_Sihi9W0fmb', 11, 1),
       ('AVAILABLE', 'RI_EyCCfGd5fD', 11, 1),
       ('AVAILABLE', 'RI_nRvM8lLOeh', 11, 3),
       ('AVAILABLE', 'RI_f2WfAzY6HD', 11, 3),
       ('AVAILABLE', 'RI_KGdYXpOTZ3', 11, 1),
       ('AVAILABLE', 'RI_jHOzmTnrKz', 11, 1),
       ('AVAILABLE', 'RI_s6dF03GY1z', 11, 4),
       ('AVAILABLE', 'RI_VgosXeIZuW', 11, 1),
       ('AVAILABLE', 'RI_fv02BKzqyX', 11, 3),
       ('AVAILABLE', 'RI_K2HVn2kAE8', 11, 3),
       ('AVAILABLE', 'RI_rJBjIRN7lL', 11, 2),
       ('AVAILABLE', 'RI_yoJToiJ6ya', 11, 1),
       ('AVAILABLE', 'RI_KOWbA52unT', 11, 3),
       ('AVAILABLE', 'RI_nqzTpo5tPo', 12, 3),
       ('AVAILABLE', 'RI_ja4xenSDdM', 12, 1),
       ('AVAILABLE', 'RI_gL1hPhUGnI', 12, 2),
       ('AVAILABLE', 'RI_kvnDzxHGYb', 12, 1),
       ('AVAILABLE', 'RI_S2WiZ4MJ5C', 12, 3),
       ('AVAILABLE', 'RI_i6HuFH5UFQ', 12, 3),
       ('AVAILABLE', 'RI_sPOoSvFXKE', 12, 1),
       ('AVAILABLE', 'RI_9kxRQ7hBS9', 12, 1),
       ('AVAILABLE', 'RI_YwXEbHDarN', 12, 2),
       ('AVAILABLE', 'RI_EAmfNv7y2s', 12, 2),
       ('AVAILABLE', 'RI_DO38TwNUY3', 12, 3),
       ('AVAILABLE', 'RI_9U740vtAls', 12, 4),
       ('AVAILABLE', 'RI_NNImeXUs8z', 12, 4),
       ('AVAILABLE', 'RI_Gl1zoP9Vrl', 12, 3),
       ('AVAILABLE', 'RI_yPG9H7aSlZ', 12, 4),
       ('AVAILABLE', 'RI_eR6RmkHQ7H', 12, 3),
       ('AVAILABLE', 'RI_bnEpBuzGVQ', 12, 2),
       ('AVAILABLE', 'RI_t0MLUhwtyk', 12, 3),
       ('AVAILABLE', 'RI_UXHUFktGGQ', 12, 1),
       ('AVAILABLE', 'RI_Lvd5keyMeQ', 12, 4),
       ('AVAILABLE', 'RI_7z2ylKw0wQ', 12, 4),
       ('AVAILABLE', 'RI_jrt8CImXSX', 12, 3),
       ('AVAILABLE', 'RI_6dERO37zMb', 12, 2),
       ('AVAILABLE', 'RI_MNdxQTV3xp', 12, 2),
       ('AVAILABLE', 'RI_cxHdn6lvb1', 12, 3),
       ('AVAILABLE', 'RI_o47rzKJ2e4', 12, 3),
       ('AVAILABLE', 'RI_SKpCHNS8aP', 12, 2),
       ('AVAILABLE', 'RI_rdDyxZs22D', 12, 2),
       ('AVAILABLE', 'RI_6r1cWt32Gu', 12, 1),
       ('AVAILABLE', 'RI_b72PbHc843', 12, 3),
       ('AVAILABLE', 'RI_cJRmsKeeU3', 12, 1),
       ('AVAILABLE', 'RI_9vcs4kQNpi', 13, 2),
       ('AVAILABLE', 'RI_MEP1hGGoEF', 13, 3),
       ('AVAILABLE', 'RI_aMsvn85HMT', 13, 3),
       ('AVAILABLE', 'RI_lmBPkjSjfs', 13, 2),
       ('AVAILABLE', 'RI_1jeLzuV8f5', 13, 2),
       ('AVAILABLE', 'RI_NjbGuSCYdj', 13, 3),
       ('AVAILABLE', 'RI_TU3EYYLobg', 13, 2),
       ('AVAILABLE', 'RI_uLvlWuvLTU', 13, 1),
       ('AVAILABLE', 'RI_aJ3N0Guzgo', 13, 4),
       ('AVAILABLE', 'RI_ahjn6Ztb53', 13, 2),
       ('AVAILABLE', 'RI_DcwacF9azB', 13, 3),
       ('AVAILABLE', 'RI_WUDfeHktwO', 13, 4),
       ('AVAILABLE', 'RI_zeq9sCbjGR', 13, 2),
       ('AVAILABLE', 'RI_My2iPQVT53', 13, 1),
       ('AVAILABLE', 'RI_GdnrHS6KK1', 13, 3),
       ('AVAILABLE', 'RI_2yS6z9vNr8', 13, 1),
       ('AVAILABLE', 'RI_qnEpFcr8sx', 13, 3),
       ('AVAILABLE', 'RI_zmkzihRbmW', 13, 2),
       ('AVAILABLE', 'RI_36CvS0ryRm', 13, 2),
       ('AVAILABLE', 'RI_l8yc76NJmj', 13, 1),
       ('AVAILABLE', 'RI_7qhPBdFySN', 13, 4),
       ('AVAILABLE', 'RI_6HdzXV7OPc', 13, 4),
       ('AVAILABLE', 'RI_qwtFAid6OL', 13, 4),
       ('AVAILABLE', 'RI_ND77t8jSVF', 14, 1),
       ('AVAILABLE', 'RI_1P8irMo2PN', 14, 4),
       ('AVAILABLE', 'RI_Y58U1QJk8U', 14, 2),
       ('AVAILABLE', 'RI_eLwXEpanXC', 14, 4),
       ('AVAILABLE', 'RI_Joq4slfMnG', 14, 2),
       ('AVAILABLE', 'RI_h8AIgwDmHq', 14, 1),
       ('AVAILABLE', 'RI_weAD3g7A7f', 14, 2),
       ('AVAILABLE', 'RI_QPL35AtEAA', 14, 4),
       ('AVAILABLE', 'RI_goK5t1Yt2i', 14, 1),
       ('AVAILABLE', 'RI_RqihT6tYmL', 14, 4),
       ('AVAILABLE', 'RI_buCfkoNdoa', 14, 2),
       ('AVAILABLE', 'RI_DfHgtPDp0Z', 14, 2),
       ('AVAILABLE', 'RI_4AVuRc58Ly', 14, 2),
       ('AVAILABLE', 'RI_ariuVaAKvb', 14, 3),
       ('AVAILABLE', 'RI_vKmZlokvHQ', 14, 3),
       ('AVAILABLE', 'RI_xsKctBR3zS', 14, 4),
       ('AVAILABLE', 'RI_i1On2hI8sH', 14, 2),
       ('AVAILABLE', 'RI_oEyWNs29XP', 14, 3),
       ('AVAILABLE', 'RI_W4zbNHxOzS', 14, 2),
       ('AVAILABLE', 'RI_LAfLR8hNrS', 14, 1),
       ('AVAILABLE', 'RI_0qeXMGdiH1', 14, 1),
       ('AVAILABLE', 'RI_W7Ok5JQOnL', 14, 3),
       ('AVAILABLE', 'RI_nRa1bWrQEE', 14, 4),
       ('AVAILABLE', 'RI_V5JicV59fF', 14, 1),
       ('AVAILABLE', 'RI_DQyAufL6Lp', 14, 3),
       ('AVAILABLE', 'RI_YYefUiDXPi', 14, 3),
       ('AVAILABLE', 'RI_VVCCs9qKnT', 14, 3),
       ('AVAILABLE', 'RI_uLH2uFpg8X', 14, 2),
       ('AVAILABLE', 'RI_X1YkzvnDHr', 14, 4),
       ('AVAILABLE', 'RI_9zmNUUXlq0', 14, 4);

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
INSERT INTO rental_history (status, rental_time_hour, start_time, expected_return_time, return_time, token, user_id,
                            rental_item_id, rental_station_id, return_station_id)
VALUES ('RETURN', '1', '2025-02-18 08:52:49', '2025-02-18 09:52:49', '2025-02-18 09:52:49', 'RH_kbVYNL5oMi', '1', '53', '4', '1'),
       ('RETURN', '2', '2025-02-04 07:52:49', '2025-02-04 09:52:49', '2025-02-04 09:52:49', 'RH_AT7OZHHzd7', '1', '42', '3', '3'),
       ('RETURN', '3', '2025-02-19 04:52:49', '2025-02-19 07:52:49', '2025-02-19 07:52:49', 'RH_GPjDG2WKs8', '1', '31', '3', '2'),
       ('RETURN', '4', '2025-02-11 02:52:49', '2025-02-11 06:52:49', '2025-02-11 06:52:49', 'RH_UcZKeTsK2M', '1', '81', '3', '4'),
       ('RETURN', '5', '2025-02-01 02:52:49', '2025-02-01 07:52:49', '2025-02-01 07:52:49', 'RH_D1rnFY33zX', '1', '60', '3', '1'),
       ('RETURN', '1', '2025-02-13 09:52:49', '2025-02-13 10:52:49', '2025-02-13 10:52:49', 'RH_944ugQnPy3', '1', '30', '3', '1'),
       ('RETURN', '2', '2025-02-01 11:52:49', '2025-02-01 13:52:49', '2025-02-01 13:52:49', 'RH_ZJsvUhQ2wZ', '1', '21', '3', '4'),
       ('RETURN', '3', '2025-02-20 01:52:49', '2025-02-20 04:52:49', '2025-02-20 04:52:49', 'RH_W6JjVQZPE8', '1', '14', '4', '4'),
       ('RETURN', '4', '2025-02-21 03:52:49', '2025-02-21 07:52:49', '2025-02-21 07:52:49', 'RH_n5LBG6OfTp', '1', '12', '2', '1'),
       ('RETURN', '5', '2025-01-31 08:52:49', '2025-01-31 13:52:49', '2025-01-31 13:52:49', 'RH_97Jq1t5v9D', '1', '26', '2', '1'),
       ('RETURN', '1', '2025-01-31 01:52:49', '2025-01-31 02:52:49', '2025-01-31 02:52:49', 'RH_m12JIiMZQC', '1', '53', '3', '1'),
       ('RETURN', '2', '2025-02-19 08:52:49', '2025-02-19 10:52:49', '2025-02-19 10:52:49', 'RH_ZEarbPsU0V', '1', '1', '4', '3'),
       ('RETURN', '3', '2025-02-05 09:52:49', '2025-02-05 12:52:49', '2025-02-05 12:52:49', 'RH_m0OAJncTgx', '1', '43', '3', '1'),
       ('RETURN', '4', '2025-02-17 00:52:49', '2025-02-17 04:52:49', '2025-02-17 04:52:49', 'RH_RdHtn5DBdA', '1', '72', '3', '1'),
       ('RETURN', '5', '2025-02-23 09:52:49', '2025-02-23 14:52:49', '2025-02-23 14:52:49', 'RH_5lpA7qPtWY', '1', '26', '1', '4'),
       ('RETURN', '1', '2025-02-22 01:52:49', '2025-02-22 02:52:49', '2025-02-22 02:52:49', 'RH_UfArFO0Mew', '1', '24', '3', '3'),
       ('RETURN', '2', '2025-01-30 08:52:49', '2025-01-30 10:52:49', '2025-01-30 10:52:49', 'RH_fCqjoGzPRC', '1', '14', '3', '1'),
       ('RETURN', '3', '2025-02-26 00:52:49', '2025-02-26 03:52:49', '2025-02-26 03:52:49', 'RH_3ZArZ77WUG', '1', '25', '4', '3'),
       ('RETURN', '4', '2025-02-16 06:52:49', '2025-02-16 10:52:49', '2025-02-16 10:52:49', 'RH_cDVq2UKtjJ', '1', '72', '3', '1'),
       ('RETURN', '5', '2025-02-03 05:52:49', '2025-02-03 10:52:49', '2025-02-03 10:52:49', 'RH_58p7CHCDbS', '1', '20', '2', '2'),
       ('RETURN', '1', '2025-02-07 07:52:49', '2025-02-07 08:52:49', '2025-02-07 08:52:49', 'RH_MmcrHi0yAE', '1', '37', '4', '1'),
       ('RETURN', '2', '2025-01-30 03:52:49', '2025-01-30 05:52:49', '2025-01-30 05:52:49', 'RH_Y30owGfW3r', '1', '84', '2', '4'),
       ('RETURN', '3', '2025-02-27 00:52:49', '2025-02-27 03:52:49', '2025-02-27 03:52:49', 'RH_5LjGQX4yrb', '1', '13', '1', '2'),
       ('RETURN', '4', '2025-02-11 05:52:49', '2025-02-11 09:52:49', '2025-02-11 09:52:49', 'RH_Z9DGGrCPvk', '1', '5', '1', '1'),
       ('RETURN', '5', '2025-02-12 07:52:49', '2025-02-12 12:52:49', '2025-02-12 12:52:49', 'RH_TF43vFRtv0', '1', '28', '4', '2'),
       ('RETURN', '1', '2025-01-31 07:52:49', '2025-01-31 08:52:49', '2025-01-31 08:52:49', 'RH_WZzch607a4', '1', '97', '4', '4'),
       ('RETURN', '2', '2025-02-01 01:52:49', '2025-02-01 03:52:49', '2025-02-01 03:52:49', 'RH_hJfNf73c94', '1', '25', '3', '3'),
       ('RETURN', '3', '2025-02-11 03:52:49', '2025-02-11 06:52:49', '2025-02-11 06:52:49', 'RH_kN6DMxhO9j', '1', '21', '1', '1'),
       ('RENTAL', '4', '2025-02-28 10:00:00', '2025-02-28 14:00:00', NULL, 'RH_9ddkgLrmLz', '1', '100', '3', '3'),
       ('RENTAL', '5', '2025-03-01 14:00:00', '2025-03-01 19:00:00', NULL, 'RH_XzLoXbmLVf', '1', '63', '4', '4');

-- BookmarkStation
INSERT INTO bookmark_stations (user_id, rental_station_id)
VALUES ('1', '1'),
       ('1', '2'),
       ('1', '4');