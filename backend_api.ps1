$port = 3000
$listener = New-Object System.Net.HttpListener
$listener.Prefixes.Add("http://127.0.0.1:3000/")
try {
    $listener.Start()
    Write-Host "Backend API is running on port $port"
    Write-Host "Press Ctrl+C to stop."

    while ($listener.IsListening) {
        $context = $listener.GetContext()
        $request = $context.Request
        $response = $context.Response
        
        $path = $request.Url.LocalPath
        Write-Host "Received request: $path"

        $json = "{}"
        if ($path -eq "/posts") {
            $json = '[{"userId": 1, "id": 1, "title": "Jadwal Ujian Akhir Semester Ganjil 2025/2026", "body": "Diberitahukan kepada seluruh mahasiswa Teknik Informatika, pelaksanaan UAS Ganjil akan dimulai pada tanggal 10 Februari 2026. Harap melengkapi semua persyaratan administrasi sebelum tanggal 5 Februari 2026."}, {"userId": 1, "id": 2, "title": "Batas Akhir Pengisian KRS Semester Genap", "body": "Batas akhir pengisian Kartu Rencana Studi (KRS) untuk semester Genap 2025/2026 adalah tanggal 20 Februari 2026. Keterlambatan pengisian akan dikenakan sanksi cuti akademik."}, {"userId": 2, "id": 3, "title": "Libur Nasional dan Cuti Bersama", "body": "Sehubungan dengan hari libur nasional, seluruh kegiatan perkuliahan ditiadakan pada tanggal 28 Februari 2026. Kegiatan akademik akan kembali aktif pada tanggal 2 Maret 2026."}, {"userId": 2, "id": 4, "title": "Pendaftaran Beasiswa Prestasi", "body": "Pendaftaran beasiswa prestasi untuk mahasiswa aktif semester 3 ke atas telah dibuka. Syarat IPK minimal 3.50. Berkas dapat diserahkan ke bagian kemahasiswaan paling lambat 15 Maret 2026."}, {"userId": 3, "id": 5, "title": "Pemeliharaan Server SIAKAD", "body": "Akan dilakukan pemeliharaan rutin pada server SIAKAD pada hari Sabtu, 15 Februari 2026 pukul 22.00 - 24.00 WIB. Selama waktu tersebut, layanan SIAKAD tidak dapat diakses."}]'
        } elseif ($path -eq "/users/1") {
            $json = '{"id": 1, "name": "Muhammad Esa priangga", "username": "esa.priangga", "email": "230201055@student.aisyah.ac.id", "phone": "0812-3456-7890", "website": "aisyah.ac.id", "address": {"street": "Jl. Aisyah", "suite": "Blok A", "city": "Pringsewu", "zipcode": "35373"}, "company": {"name": "Universitas Aisyah Pringsewu", "catchPhrase": "Teknik Informatika", "bs": "Semester 5"}}'
        } elseif ($path -match "^/posts/(\d+)") {
            $id = $matches[1]
            $json = '{"userId": 1, "id": 1, "title": "Jadwal Ujian Akhir Semester Ganjil 2025/2026", "body": "Diberitahukan kepada seluruh mahasiswa Teknik Informatika, pelaksanaan UAS Ganjil akan dimulai pada tanggal 10 Februari 2026. Harap melengkapi semua persyaratan administrasi sebelum tanggal 5 Februari 2026."}'
        }

        $buffer = [System.Text.Encoding]::UTF8.GetBytes($json)
        $response.ContentLength64 = $buffer.Length
        $response.ContentType = "application/json"
        $response.OutputStream.Write($buffer, 0, $buffer.Length)
        $response.Close()
    }
} catch {
    Write-Host "Error: $_"
} finally {
    if ($listener.IsListening) {
        $listener.Stop()
    }
}
