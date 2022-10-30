import 'package:crime_reporting/loading.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'main.dart';
import 'package:google_fonts/google_fonts.dart';

class Splash extends StatefulWidget {
  const Splash({super.key});

  @override
  State<Splash> createState() => _SplashState();
}

class _SplashState extends State<Splash> {
  @override
  void initState() {
    super.initState();
    _navigatetohome();
  }

  _navigatetohome() async {
    await Future.delayed(const Duration(milliseconds: 3000), () {});
    Navigator.pushReplacement(
      context,
      MaterialPageRoute(
        builder: (context) => const Loading(),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: Container(
      height: double.infinity,
      width: double.infinity,
      decoration:
          const BoxDecoration(color: Color.fromARGB(255, 255, 255, 255)),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Container(
              height: 150,
              width: 150,
              margin: const EdgeInsets.only(bottom: 30),
              //decoration: const BoxDecoration(color: Colors.purple),
              child: Image.asset(
                  "${(kDebugMode && kIsWeb) ? "" : "assets/"}logo.png")),
          Text(
            'Crime Reporting',
            style: GoogleFonts.gemunuLibre(
                fontWeight: FontWeight.bold,
                fontSize: 30,
                textStyle: TextStyle(color: Colors.blue)),
          ),
        ],
      ),
    ));
  }
}
